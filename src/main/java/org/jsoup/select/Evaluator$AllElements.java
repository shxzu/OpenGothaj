package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$AllElements
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        return true;
    }

    public String toString() {
        return "*";
    }
}
