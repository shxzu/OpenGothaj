package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeVisitor;

class Collector$Accumulator
implements NodeVisitor {
    private final Element root;
    private final Elements elements;
    private final Evaluator eval;

    Collector$Accumulator(Element root, Elements elements, Evaluator eval) {
        this.root = root;
        this.elements = elements;
        this.eval = eval;
    }

    @Override
    public void head(Node node, int depth) {
        Element el;
        if (node instanceof Element && this.eval.matches(this.root, el = (Element)node)) {
            this.elements.add(el);
        }
    }

    @Override
    public void tail(Node node, int depth) {
    }
}
