package org.jsoup.select;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsFirstChild
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        Element p = element.parent();
        return p != null && !(p instanceof Document) && element.elementSiblingIndex() == 0;
    }

    public String toString() {
        return ":first-child";
    }
}
