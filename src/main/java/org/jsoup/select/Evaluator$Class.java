package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$Class
extends Evaluator {
    private final String className;

    public Evaluator$Class(String className) {
        this.className = className;
    }

    @Override
    public boolean matches(Element root, Element element) {
        return element.hasClass(this.className);
    }

    public String toString() {
        return String.format(".%s", this.className);
    }
}
