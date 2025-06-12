package org.jsoup.select;

import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$ContainsData
extends Evaluator {
    private final String searchText;

    public Evaluator$ContainsData(String searchText) {
        this.searchText = Normalizer.lowerCase(searchText);
    }

    @Override
    public boolean matches(Element root, Element element) {
        return Normalizer.lowerCase(element.data()).contains(this.searchText);
    }

    public String toString() {
        return String.format(":containsData(%s)", this.searchText);
    }
}
