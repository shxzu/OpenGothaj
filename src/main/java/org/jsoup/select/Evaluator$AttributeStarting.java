package org.jsoup.select;

import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$AttributeStarting
extends Evaluator {
    private final String keyPrefix;

    public Evaluator$AttributeStarting(String keyPrefix) {
        Validate.notEmpty(keyPrefix);
        this.keyPrefix = Normalizer.lowerCase(keyPrefix);
    }

    @Override
    public boolean matches(Element root, Element element) {
        List<Attribute> values = element.attributes().asList();
        for (Attribute attribute : values) {
            if (!Normalizer.lowerCase(attribute.getKey()).startsWith(this.keyPrefix)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format("[^%s]", this.keyPrefix);
    }
}
