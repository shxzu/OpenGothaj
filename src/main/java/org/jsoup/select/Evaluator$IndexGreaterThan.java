package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IndexGreaterThan
extends Evaluator.IndexEvaluator {
    public Evaluator$IndexGreaterThan(int index) {
        super(index);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.elementSiblingIndex() > this.index;
    }

    public String toString() {
        return String.format(":gt(%d)", this.index);
    }
}
