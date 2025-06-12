package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$ContainsWholeOwnText
extends Evaluator {
    private final String searchText;

    public Evaluator$ContainsWholeOwnText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.wholeOwnText().contains(this.searchText);
    }

    public String toString() {
        return String.format(":containsWholeOwnText(%s)", this.searchText);
    }
}
