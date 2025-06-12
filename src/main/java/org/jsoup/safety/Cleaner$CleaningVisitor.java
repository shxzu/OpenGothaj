package org.jsoup.safety;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.safety.Cleaner;
import org.jsoup.select.NodeVisitor;

final class Cleaner$CleaningVisitor
implements NodeVisitor {
    private int numDiscarded = 0;
    private final Element root;
    private Element destination;

    private Cleaner$CleaningVisitor(Element root, Element destination) {
        this.root = root;
        this.destination = destination;
    }

    @Override
    public void head(Node source, int depth) {
        if (source instanceof Element) {
            Element sourceEl = (Element)source;
            if (Cleaner.this.safelist.isSafeTag(sourceEl.normalName())) {
                Cleaner.ElementMeta meta = Cleaner.this.createSafeElement(sourceEl);
                Element destChild = meta.el;
                this.destination.appendChild(destChild);
                this.numDiscarded += meta.numAttribsDiscarded;
                this.destination = destChild;
            } else if (source != this.root) {
                ++this.numDiscarded;
            }
        } else if (source instanceof TextNode) {
            TextNode sourceText = (TextNode)source;
            TextNode destText = new TextNode(sourceText.getWholeText());
            this.destination.appendChild(destText);
        } else if (source instanceof DataNode && Cleaner.this.safelist.isSafeTag(source.parent().nodeName())) {
            DataNode sourceData = (DataNode)source;
            DataNode destData = new DataNode(sourceData.getWholeData());
            this.destination.appendChild(destData);
        } else {
            ++this.numDiscarded;
        }
    }

    @Override
    public void tail(Node source, int depth) {
        if (source instanceof Element && Cleaner.this.safelist.isSafeTag(source.nodeName())) {
            this.destination = this.destination.parent();
        }
    }

    static int access$300(Cleaner$CleaningVisitor x0) {
        return x0.numDiscarded;
    }
}
