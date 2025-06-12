package org.jsoup.helper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class W3CDom {
    public static final String SourceProperty = "jsoupSource";
    private static final String ContextProperty = "jsoupContextSource";
    private static final String ContextNodeProperty = "jsoupContextNode";
    public static final String XPathFactoryProperty = "javax.xml.xpath.XPathFactory:jsoup";
    protected DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private boolean namespaceAware = true;

    public W3CDom() {
        this.factory.setNamespaceAware(true);
    }

    public boolean namespaceAware() {
        return this.namespaceAware;
    }

    public W3CDom namespaceAware(boolean namespaceAware) {
        this.namespaceAware = namespaceAware;
        this.factory.setNamespaceAware(namespaceAware);
        return this;
    }

    public static org.w3c.dom.Document convert(Document in) {
        return new W3CDom().fromJsoup(in);
    }

    public static String asString(org.w3c.dom.Document doc, @Nullable Map<String, String> properties) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            if (properties != null) {
                transformer.setOutputProperties(W3CDom.propertiesFromMap(properties));
            }
            if (doc.getDoctype() != null) {
                DocumentType doctype = doc.getDoctype();
                if (!StringUtil.isBlank(doctype.getPublicId())) {
                    transformer.setOutputProperty("doctype-public", doctype.getPublicId());
                }
                if (!StringUtil.isBlank(doctype.getSystemId())) {
                    transformer.setOutputProperty("doctype-system", doctype.getSystemId());
                } else if (doctype.getName().equalsIgnoreCase("html") && StringUtil.isBlank(doctype.getPublicId()) && StringUtil.isBlank(doctype.getSystemId())) {
                    transformer.setOutputProperty("doctype-system", "about:legacy-compat");
                }
            }
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
    }

    static Properties propertiesFromMap(Map<String, String> map) {
        Properties props = new Properties();
        props.putAll(map);
        return props;
    }

    public static HashMap<String, String> OutputHtml() {
        return W3CDom.methodMap("html");
    }

    public static HashMap<String, String> OutputXml() {
        return W3CDom.methodMap("xml");
    }

    private static HashMap<String, String> methodMap(String method) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("method", method);
        return map;
    }

    public org.w3c.dom.Document fromJsoup(Document in) {
        return this.fromJsoup((org.jsoup.nodes.Element)in);
    }

    public org.w3c.dom.Document fromJsoup(org.jsoup.nodes.Element in) {
        Validate.notNull(in);
        try {
            org.jsoup.nodes.DocumentType doctype;
            DocumentBuilder builder = this.factory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();
            org.w3c.dom.Document out = builder.newDocument();
            Document inDoc = in.ownerDocument();
            org.jsoup.nodes.DocumentType documentType = doctype = inDoc != null ? inDoc.documentType() : null;
            if (doctype != null) {
                DocumentType documentType2 = impl.createDocumentType(doctype.name(), doctype.publicId(), doctype.systemId());
                out.appendChild(documentType2);
            }
            out.setXmlStandalone(true);
            org.jsoup.nodes.Element context = in instanceof Document ? in.child(0) : in;
            out.setUserData(ContextProperty, context, null);
            this.convert(inDoc != null ? inDoc : in, out);
            return out;
        }
        catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public void convert(Document in, org.w3c.dom.Document out) {
        this.convert((org.jsoup.nodes.Element)in, out);
    }

    public void convert(org.jsoup.nodes.Element in, org.w3c.dom.Document out) {
        W3CBuilder builder = new W3CBuilder(out);
        builder.namespaceAware = this.namespaceAware;
        Document inDoc = in.ownerDocument();
        if (inDoc != null) {
            if (!StringUtil.isBlank(inDoc.location())) {
                out.setDocumentURI(inDoc.location());
            }
            builder.syntax = inDoc.outputSettings().syntax();
        }
        org.jsoup.nodes.Element rootEl = in instanceof Document ? in.child(0) : in;
        NodeTraversor.traverse((NodeVisitor)builder, rootEl);
    }

    public NodeList selectXpath(String xpath, org.w3c.dom.Document doc) {
        return this.selectXpath(xpath, (org.w3c.dom.Node)doc);
    }

    public NodeList selectXpath(String xpath, org.w3c.dom.Node contextNode) {
        NodeList nodeList;
        Validate.notEmptyParam(xpath, "xpath");
        Validate.notNullParam(contextNode, "contextNode");
        try {
            String property = System.getProperty(XPathFactoryProperty);
            XPathFactory xPathFactory = property != null ? XPathFactory.newInstance("jsoup") : XPathFactory.newInstance();
            XPathExpression expression = xPathFactory.newXPath().compile(xpath);
            nodeList = (NodeList)expression.evaluate(contextNode, XPathConstants.NODESET);
            Validate.notNull(nodeList);
        }
        catch (XPathExpressionException | XPathFactoryConfigurationException e) {
            throw new Selector.SelectorParseException("Could not evaluate XPath query [%s]: %s", xpath, e.getMessage());
        }
        return nodeList;
    }

    public <T extends Node> List<T> sourceNodes(NodeList nodeList, Class<T> nodeType) {
        Validate.notNull(nodeList);
        Validate.notNull(nodeType);
        ArrayList<Node> nodes = new ArrayList<Node>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); ++i) {
            org.w3c.dom.Node node = nodeList.item(i);
            Object source = node.getUserData(SourceProperty);
            if (!nodeType.isInstance(source)) continue;
            nodes.add((Node)nodeType.cast(source));
        }
        return nodes;
    }

    public org.w3c.dom.Node contextNode(org.w3c.dom.Document wDoc) {
        return (org.w3c.dom.Node)wDoc.getUserData(ContextNodeProperty);
    }

    public String asString(org.w3c.dom.Document doc) {
        return W3CDom.asString(doc, null);
    }

    protected static class W3CBuilder
    implements NodeVisitor {
        private static final String xmlnsKey = "xmlns";
        private static final String xmlnsPrefix = "xmlns:";
        private final org.w3c.dom.Document doc;
        private boolean namespaceAware = true;
        private final Stack<HashMap<String, String>> namespacesStack = new Stack();
        private org.w3c.dom.Node dest;
        private Document.OutputSettings.Syntax syntax = Document.OutputSettings.Syntax.xml;
        @Nullable
        private final org.jsoup.nodes.Element contextElement;

        public W3CBuilder(org.w3c.dom.Document doc) {
            this.doc = doc;
            this.namespacesStack.push(new HashMap());
            this.dest = doc;
            this.contextElement = (org.jsoup.nodes.Element)doc.getUserData(W3CDom.ContextProperty);
        }

        @Override
        public void head(Node source, int depth) {
            this.namespacesStack.push(new HashMap(this.namespacesStack.peek()));
            if (source instanceof org.jsoup.nodes.Element) {
                org.jsoup.nodes.Element sourceEl = (org.jsoup.nodes.Element)source;
                String prefix = this.updateNamespaces(sourceEl);
                String namespace = this.namespaceAware ? this.namespacesStack.peek().get(prefix) : null;
                String tagName = sourceEl.tagName();
                try {
                    Element el = namespace == null && tagName.contains(":") ? this.doc.createElementNS("", tagName) : this.doc.createElementNS(namespace, tagName);
                    this.copyAttributes(sourceEl, el);
                    this.append(el, sourceEl);
                    if (sourceEl == this.contextElement) {
                        this.doc.setUserData(W3CDom.ContextNodeProperty, el, null);
                    }
                    this.dest = el;
                }
                catch (DOMException e) {
                    this.append(this.doc.createTextNode("<" + tagName + ">"), sourceEl);
                }
            } else if (source instanceof TextNode) {
                TextNode sourceText = (TextNode)source;
                Text text = this.doc.createTextNode(sourceText.getWholeText());
                this.append(text, sourceText);
            } else if (source instanceof org.jsoup.nodes.Comment) {
                org.jsoup.nodes.Comment sourceComment = (org.jsoup.nodes.Comment)source;
                Comment comment = this.doc.createComment(sourceComment.getData());
                this.append(comment, sourceComment);
            } else if (source instanceof DataNode) {
                DataNode sourceData = (DataNode)source;
                Text node = this.doc.createTextNode(sourceData.getWholeData());
                this.append(node, sourceData);
            }
        }

        private void append(org.w3c.dom.Node append, Node source) {
            append.setUserData(W3CDom.SourceProperty, source, null);
            this.dest.appendChild(append);
        }

        @Override
        public void tail(Node source, int depth) {
            if (source instanceof org.jsoup.nodes.Element && this.dest.getParentNode() instanceof Element) {
                this.dest = this.dest.getParentNode();
            }
            this.namespacesStack.pop();
        }

        private void copyAttributes(Node source, Element el) {
            for (Attribute attribute : source.attributes()) {
                String key = Attribute.getValidKey(attribute.getKey(), this.syntax);
                if (key == null) continue;
                el.setAttribute(key, attribute.getValue());
            }
        }

        private String updateNamespaces(org.jsoup.nodes.Element el) {
            Attributes attributes = el.attributes();
            for (Attribute attr : attributes) {
                String prefix;
                String key = attr.getKey();
                if (key.equals(xmlnsKey)) {
                    prefix = "";
                } else {
                    if (!key.startsWith(xmlnsPrefix)) continue;
                    prefix = key.substring(xmlnsPrefix.length());
                }
                this.namespacesStack.peek().put(prefix, attr.getValue());
            }
            int pos = el.tagName().indexOf(58);
            return pos > 0 ? el.tagName().substring(0, pos) : "";
        }
    }
}
