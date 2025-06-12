package org.jsoup.select;

import javax.annotation.Nullable;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeTraversor;

class Collector$FirstFinder
implements NodeFilter {
    @Nullable
    private Element evalRoot = null;
    @Nullable
    private Element match = null;
    private final Evaluator eval;

    Collector$FirstFinder(Evaluator eval) {
        this.eval = eval;
    }

    @Nullable
    Element find(Element root, Element start) {
        this.evalRoot = root;
        this.match = null;
        NodeTraversor.filter((NodeFilter)this, start);
        return this.match;
    }

    @Override
    public NodeFilter.FilterResult head(Node node, int depth) {
        Element el;
        if (node instanceof Element && this.eval.matches(this.evalRoot, el = (Element)node)) {
            this.match = el;
            return NodeFilter.FilterResult.STOP;
        }
        return NodeFilter.FilterResult.CONTINUE;
    }

    @Override
    public NodeFilter.FilterResult tail(Node node, int depth) {
        return NodeFilter.FilterResult.CONTINUE;
    }
}
