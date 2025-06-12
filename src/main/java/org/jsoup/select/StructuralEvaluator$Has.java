package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Collector;
import org.jsoup.select.Evaluator;
import org.jsoup.select.StructuralEvaluator;

class StructuralEvaluator$Has
extends StructuralEvaluator {
    final Collector.FirstFinder finder;

    public StructuralEvaluator$Has(Evaluator evaluator) {
        this.evaluator = evaluator;
        this.finder = new Collector.FirstFinder(evaluator);
    }

    @Override
    public boolean matches(Element root, Element element) {
        for (int i = 0; i < element.childNodeSize(); ++i) {
            Element match;
            Node node = element.childNode(i);
            if (!(node instanceof Element) || (match = this.finder.find(element, (Element)node)) == null) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format(":has(%s)", this.evaluator);
    }
}
