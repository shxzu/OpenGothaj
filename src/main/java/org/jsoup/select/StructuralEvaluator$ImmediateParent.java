package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;
import org.jsoup.select.StructuralEvaluator;

class StructuralEvaluator$ImmediateParent
extends StructuralEvaluator {
    public StructuralEvaluator$ImmediateParent(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean matches(Element root, Element element) {
        if (root == element) {
            return false;
        }
        Element parent = element.parent();
        return parent != null && this.evaluator.matches(root, parent);
    }

    public String toString() {
        return String.format("%s > ", this.evaluator);
    }
}
