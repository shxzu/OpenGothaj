package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.SerializationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

class Node$OuterHtmlVisitor
implements NodeVisitor {
    private final Appendable accum;
    private final Document.OutputSettings out;

    Node$OuterHtmlVisitor(Appendable accum, Document.OutputSettings out) {
        this.accum = accum;
        this.out = out;
        out.prepareEncoder();
    }

    @Override
    public void head(Node node, int depth) {
        try {
            node.outerHtmlHead(this.accum, depth, this.out);
        }
        catch (IOException exception) {
            throw new SerializationException(exception);
        }
    }

    @Override
    public void tail(Node node, int depth) {
        if (!node.nodeName().equals("#text")) {
            try {
                node.outerHtmlTail(this.accum, depth, this.out);
            }
            catch (IOException exception) {
                throw new SerializationException(exception);
            }
        }
    }
}
