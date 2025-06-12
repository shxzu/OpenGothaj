package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.UncheckedIOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;

public class CDataNode
extends TextNode {
    public CDataNode(String text) {
        super(text);
    }

    @Override
    public String nodeName() {
        return "#cdata";
    }

    @Override
    public String text() {
        return this.getWholeText();
    }

    @Override
    void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        accum.append("<![CDATA[").append(this.getWholeText());
    }

    @Override
    void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
        try {
            accum.append("]]>");
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public CDataNode clone() {
        return (CDataNode)super.clone();
    }
}
