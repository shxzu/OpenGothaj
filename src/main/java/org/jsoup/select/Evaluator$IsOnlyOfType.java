package org.jsoup.select;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsOnlyOfType
extends Evaluator {
    @Override
    public boolean matches(Element root, Element element) {
        Element p = element.parent();
        if (p == null || p instanceof Document) {
            return false;
        }
        int pos = 0;
        Elements family = p.children();
        for (Element el : family) {
            if (!el.tag().equals(element.tag())) continue;
            ++pos;
        }
        return pos == 1;
    }

    public String toString() {
        return ":only-of-type";
    }
}
