package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;
import org.jsoup.select.StructuralEvaluator;

class StructuralEvaluator$Not
extends StructuralEvaluator {
    public StructuralEvaluator$Not(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean matches(Element root, Element node) {
        return !this.evaluator.matches(root, node);
    }

    public String toString() {
        return String.format(":not(%s)", this.evaluator);
    }
}
