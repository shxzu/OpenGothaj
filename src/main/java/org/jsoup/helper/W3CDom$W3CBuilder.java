package org.jsoup.helper;

import java.util.HashMap;
import java.util.Stack;
import javax.annotation.Nullable;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

class W3CDom$W3CBuilder
implements NodeVisitor {
    private static final String xmlnsKey = "xmlns";
    private static final String xmlnsPrefix = "xmlns:";
    private final Document doc;
    private boolean namespaceAware = true;
    private final Stack<HashMap<String, String>> namespacesStack = new Stack();
    private org.w3c.dom.Node dest;
    private Document.OutputSettings.Syntax syntax = Document.OutputSettings.Syntax.xml;
    @Nullable
    private final org.jsoup.nodes.Element contextElement;

    public W3CDom$W3CBuilder(Document doc) {
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
        } else if (source instanceof Comment) {
            Comment sourceComment = (Comment)source;
            org.w3c.dom.Comment comment = this.doc.createComment(sourceComment.getData());
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

    static boolean access$002(W3CDom$W3CBuilder x0, boolean x1) {
        x0.namespaceAware = x1;
        return x0.namespaceAware;
    }

    static Document.OutputSettings.Syntax access$102(W3CDom$W3CBuilder x0, Document.OutputSettings.Syntax x1) {
        x0.syntax = x1;
        return x0.syntax;
    }
}
