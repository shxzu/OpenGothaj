package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$AttributeWithValueNot
extends Evaluator.AttributeKeyPair {
    public Evaluator$AttributeWithValueNot(String key, String value) {
        super(key, value);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return !this.value.equalsIgnoreCase(element.attr(this.key));
    }

    public String toString() {
        return String.format("[%s!=%s]", this.key, this.value);
    }
}
