package org.jsoup.select;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsRoot
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        Element r = root instanceof Document ? root.child(0) : root;
        return element == r;
    }

    public String toString() {
        return ":root";
    }
}
