package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IndexEquals
extends Evaluator.IndexEvaluator {
    public Evaluator$IndexEquals(int index) {
        super(index);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.elementSiblingIndex() == this.index;
    }

    public String toString() {
        return String.format(":eq(%d)", this.index);
    }
}
