package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

class StructuralEvaluator$Root
extends Evaluator {
    StructuralEvaluator$Root() {
    }

    @Override
    public boolean matches(Element root, Element element) {
        return root == element;
    }
}
