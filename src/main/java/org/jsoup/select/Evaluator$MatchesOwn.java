package org.jsoup.select;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$MatchesOwn
extends Evaluator {
    private final Pattern pattern;

    public Evaluator$MatchesOwn(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(Element root, Element element) {
        Matcher m = this.pattern.matcher(element.ownText());
        return m.find();
    }

    public String toString() {
        return String.format(":matchesOwn(%s)", this.pattern);
    }
}
