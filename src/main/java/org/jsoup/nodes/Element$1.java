package org.jsoup.nodes;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

class Element$1
implements NodeVisitor {
    final StringBuilder val$accum;

    Element$1() {
        this.val$accum = stringBuilder;
    }

    @Override
    public void head(Node node, int depth) {
        if (node instanceof TextNode) {
            TextNode textNode = (TextNode)node;
            Element.appendNormalisedText(this.val$accum, textNode);
        } else if (node instanceof Element) {
            Element element = (Element)node;
            if (this.val$accum.length() > 0 && (element.isBlock() || element.tag.normalName().equals("br")) && !TextNode.lastCharIsWhitespace(this.val$accum)) {
                this.val$accum.append(' ');
            }
        }
    }

    @Override
    public void tail(Node node, int depth) {
        Element element;
        if (node instanceof Element && (element = (Element)node).isBlock() && node.nextSibling() instanceof TextNode && !TextNode.lastCharIsWhitespace(this.val$accum)) {
            this.val$accum.append(' ');
        }
    }
}
