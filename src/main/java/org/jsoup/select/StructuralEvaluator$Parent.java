package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;
import org.jsoup.select.StructuralEvaluator;

class StructuralEvaluator$Parent
extends StructuralEvaluator {
    public StructuralEvaluator$Parent(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean matches(Element root, Element element) {
        if (root == element) {
            return false;
        }
        for (Element parent = element.parent(); parent != null; parent = parent.parent()) {
            if (this.evaluator.matches(root, parent)) {
                return true;
            }
            if (parent == root) break;
        }
        return false;
    }

    public String toString() {
        return String.format("%s ", this.evaluator);
    }
}
