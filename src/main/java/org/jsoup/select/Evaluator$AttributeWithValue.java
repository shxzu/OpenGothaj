package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$AttributeWithValue
extends Evaluator.AttributeKeyPair {
    public Evaluator$AttributeWithValue(String key, String value) {
        super(key, value);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.hasAttr(this.key) && this.value.equalsIgnoreCase(element.attr(this.key).trim());
    }

    public String toString() {
        return String.format("[%s=%s]", this.key, this.value);
    }
}
