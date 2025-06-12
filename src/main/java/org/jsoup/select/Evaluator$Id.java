package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$Id
extends Evaluator {
    private final String id;

    public Evaluator$Id(String id) {
        this.id = id;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return this.id.equals(element.id());
    }

    public String toString() {
        return String.format("#%s", this.id);
    }
}
