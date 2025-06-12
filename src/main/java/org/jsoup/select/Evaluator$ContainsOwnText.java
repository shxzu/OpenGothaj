package org.jsoup.select;

import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$ContainsOwnText
extends Evaluator {
    private final String searchText;

    public Evaluator$ContainsOwnText(String searchText) {
        this.searchText = Normalizer.lowerCase(StringUtil.normaliseWhitespace(searchText));
    }

    @Override
    public boolean matches(Element root, Element element) {
        return Normalizer.lowerCase(element.ownText()).contains(this.searchText);
    }

    public String toString() {
        return String.format(":containsOwn(%s)", this.searchText);
    }
}
