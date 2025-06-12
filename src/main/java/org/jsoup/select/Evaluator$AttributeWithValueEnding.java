package org.jsoup.select;

import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$AttributeWithValueEnding
extends Evaluator.AttributeKeyPair {
    public Evaluator$AttributeWithValueEnding(String key, String value) {
        super(key, value, false);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.hasAttr(this.key) && Normalizer.lowerCase(element.attr(this.key)).endsWith(this.value);
    }

    public String toString() {
        return String.format("[%s$=%s]", this.key, this.value);
    }
}
