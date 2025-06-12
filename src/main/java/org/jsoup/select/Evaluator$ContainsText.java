package org.jsoup.select;

import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$ContainsText
extends Evaluator {
    private final String searchText;

    public Evaluator$ContainsText(String searchText) {
        this.searchText = Normalizer.lowerCase(StringUtil.normaliseWhitespace(searchText));
    }

    @Override
    public boolean matches(Element root, Element element) {
        return Normalizer.lowerCase(element.text()).contains(this.searchText);
    }

    public String toString() {
        return String.format(":contains(%s)", this.searchText);
    }
}
