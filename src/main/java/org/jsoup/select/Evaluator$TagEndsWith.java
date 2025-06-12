package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$TagEndsWith
extends Evaluator {
    private final String tagName;

    public Evaluator$TagEndsWith(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.normalName().endsWith(this.tagName);
    }

    public String toString() {
        return String.format("%s", this.tagName);
    }
}
