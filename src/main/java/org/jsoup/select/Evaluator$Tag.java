package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$Tag
extends Evaluator {
    private final String tagName;

    public Evaluator$Tag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.normalName().equals(this.tagName);
    }

    public String toString() {
        return String.format("%s", this.tagName);
    }
}
