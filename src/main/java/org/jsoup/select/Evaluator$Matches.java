package org.jsoup.select;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$Matches
extends Evaluator {
    private final Pattern pattern;

    public Evaluator$Matches(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(Element root, Element element) {
        Matcher m = this.pattern.matcher(element.text());
        return m.find();
    }

    public String toString() {
        return String.format(":matches(%s)", this.pattern);
    }
}
