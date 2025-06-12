package org.jsoup.select;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsOnlyChild
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        Element p = element.parent();
        return p != null && !(p instanceof Document) && element.siblingElements().isEmpty();
    }

    public String toString() {
        return ":only-child";
    }
}
