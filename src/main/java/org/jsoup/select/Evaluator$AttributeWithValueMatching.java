package org.jsoup.select;

import java.util.regex.Pattern;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$AttributeWithValueMatching
extends Evaluator {
    String key;
    Pattern pattern;

    public Evaluator$AttributeWithValueMatching(String key, Pattern pattern) {
        this.key = Normalizer.normalize(key);
        this.pattern = pattern;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.hasAttr(this.key) && this.pattern.matcher(element.attr(this.key)).find();
    }

    public String toString() {
        return String.format("[%s~=%s]", this.key, this.pattern.toString());
    }
}
