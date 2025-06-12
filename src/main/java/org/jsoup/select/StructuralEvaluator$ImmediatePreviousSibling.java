package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;
import org.jsoup.select.StructuralEvaluator;

class StructuralEvaluator$ImmediatePreviousSibling
extends StructuralEvaluator {
    public StructuralEvaluator$ImmediatePreviousSibling(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean matches(Element root, Element element) {
        if (root == element) {
            return false;
        }
        Element prev = element.previousElementSibling();
        return prev != null && this.evaluator.matches(root, prev);
    }

    public String toString() {
        return String.format("%s + ", this.evaluator);
    }
}
