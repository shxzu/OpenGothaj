package org.jsoup.select;

import javax.annotation.Nullable;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class Collector {
    private Collector() {
    }

    public static Elements collect(Evaluator eval, Element root) {
        Elements elements = new Elements();
        NodeTraversor.traverse((NodeVisitor)new Accumulator(root, elements, eval), root);
        return elements;
    }

    @Nullable
    public static Element findFirst(Evaluator eval, Element root) {
        FirstFinder finder = new FirstFinder(eval);
        return finder.find(root, root);
    }

    private static class Accumulator
    implements NodeVisitor {
        private final Element root;
        private final Elements elements;
        private final Evaluator eval;

        Accumulator(Element root, Elements elements, Evaluator eval) {
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

    static class FirstFinder
    implements NodeFilter {
        @Nullable
        private Element evalRoot = null;
        @Nullable
        private Element match = null;
        private final Evaluator eval;

        FirstFinder(Evaluator eval) {
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
}
