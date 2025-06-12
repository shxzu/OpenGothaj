package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$Attribute
extends Evaluator {
    private final String key;

    public Evaluator$Attribute(String key) {
        this.key = key;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.hasAttr(this.key);
    }

    public String toString() {
        return String.format("[%s]", this.key);
    }
}
