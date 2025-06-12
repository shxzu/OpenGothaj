package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$ContainsWholeText
extends Evaluator {
    private final String searchText;

    public Evaluator$ContainsWholeText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.wholeText().contains(this.searchText);
    }

    public String toString() {
        return String.format(":containsWholeText(%s)", this.searchText);
    }
}
