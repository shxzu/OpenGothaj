package org.jsoup.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.parser.Token;
import org.jsoup.parser.TreeBuilder;

public class XmlTreeBuilder
extends TreeBuilder {
    private static final int maxQueueDepth = 256;

    @Override
    ParseSettings defaultSettings() {
        return ParseSettings.preserveCase;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void initialiseParse(Reader input, String baseUri, Parser parser) {
        super.initialiseParse(input, baseUri, parser);
        this.stack.add(this.doc);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml).prettyPrint(false);
    }

    Document parse(Reader input, String baseUri) {
        return this.parse(input, baseUri, new Parser(this));
    }

    Document parse(String input, String baseUri) {
        return this.parse(new StringReader(input), baseUri, new Parser(this));
    }

    @Override
    XmlTreeBuilder newInstance() {
        return new XmlTreeBuilder();
    }

    @Override
    protected boolean process(Token token) {
        switch (token.type) {
            case StartTag: {
                this.insert(token.asStartTag());
                break;
            }
            case EndTag: {
                this.popStackToClose(token.asEndTag());
                break;
            }
            case Comment: {
                this.insert(token.asComment());
                break;
            }
            case Character: {
                this.insert(token.asCharacter());
                break;
            }
            case Doctype: {
                this.insert(token.asDoctype());
                break;
            }
            case EOF: {
                break;
            }
            default: {
                Validate.fail("Unexpected token type: " + (Object)((Object)token.type));
            }
        }
        return true;
    }

    protected void insertNode(Node node) {
        this.currentElement().appendChild(node);
        this.onNodeInserted(node, null);
    }

    protected void insertNode(Node node, Token token) {
        this.currentElement().appendChild(node);
        this.onNodeInserted(node, token);
    }

    Element insert(Token.StartTag startTag) {
        Tag tag = this.tagFor(startTag.name(), this.settings);
        if (startTag.hasAttributes()) {
            startTag.attributes.deduplicate(this.settings);
        }
        Element el = new Element(tag, null, this.settings.normalizeAttributes(startTag.attributes));
        this.insertNode(el, startTag);
        if (startTag.isSelfClosing()) {
            if (!tag.isKnownTag()) {
                tag.setSelfClosing();
            }
        } else {
            this.stack.add(el);
        }
        return el;
    }

    void insert(Token.Comment commentToken) {
        XmlDeclaration decl;
        Comment comment;
        LeafNode insert = comment = new Comment(commentToken.getData());
        if (commentToken.bogus && comment.isXmlDeclaration() && (decl = comment.asXmlDeclaration()) != null) {
            insert = decl;
        }
        this.insertNode(insert, commentToken);
    }

    void insert(Token.Character token) {
        String data = token.getData();
        this.insertNode(token.isCData() ? new CDataNode(data) : new TextNode(data), token);
    }

    void insert(Token.Doctype d) {
        DocumentType doctypeNode = new DocumentType(this.settings.normalizeTag(d.getName()), d.getPublicIdentifier(), d.getSystemIdentifier());
        doctypeNode.setPubSysKey(d.getPubSysKey());
        this.insertNode(doctypeNode, d);
    }

    protected void popStackToClose(Token.EndTag endTag) {
        Element next;
        int pos;
        String elName = this.settings.normalizeTag(endTag.tagName);
        Element firstFound = null;
        int bottom = this.stack.size() - 1;
        int upper = bottom >= 256 ? bottom - 256 : 0;
        for (pos = this.stack.size() - 1; pos >= upper; --pos) {
            next = (Element)this.stack.get(pos);
            if (!next.nodeName().equals(elName)) continue;
            firstFound = next;
            break;
        }
        if (firstFound == null) {
            return;
        }
        for (pos = this.stack.size() - 1; pos >= 0; --pos) {
            next = (Element)this.stack.get(pos);
            this.stack.remove(pos);
            if (next != firstFound) continue;
            this.onNodeClosed(next, endTag);
            break;
        }
    }

    List<Node> parseFragment(String inputFragment, String baseUri, Parser parser) {
        this.initialiseParse(new StringReader(inputFragment), baseUri, parser);
        this.runParser();
        return this.doc.childNodes();
    }

    @Override
    List<Node> parseFragment(String inputFragment, Element context, String baseUri, Parser parser) {
        return this.parseFragment(inputFragment, baseUri, parser);
    }
}
