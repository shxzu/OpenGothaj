package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IndexLessThan
extends Evaluator.IndexEvaluator {
    public Evaluator$IndexLessThan(int index) {
        super(index);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return root != element && element.elementSiblingIndex() < this.index;
    }

    public String toString() {
        return String.format(":lt(%d)", this.index);
    }
}
