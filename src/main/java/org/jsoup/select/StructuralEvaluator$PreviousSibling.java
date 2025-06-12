package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;
import org.jsoup.select.StructuralEvaluator;

class StructuralEvaluator$PreviousSibling
extends StructuralEvaluator {
    public StructuralEvaluator$PreviousSibling(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean matches(Element root, Element element) {
        if (root == element) {
            return false;
        }
        for (Element prev = element.previousElementSibling(); prev != null; prev = prev.previousElementSibling()) {
            if (!this.evaluator.matches(root, prev)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format("%s ~ ", this.evaluator);
    }
}
