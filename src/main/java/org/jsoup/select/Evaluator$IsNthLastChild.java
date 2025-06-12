package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsNthLastChild
extends Evaluator.CssNthEvaluator {
    public Evaluator$IsNthLastChild(int a, int b) {
        super(a, b);
    }

    @Override
    protected int calculatePosition(Element root, Element element) {
        if (element.parent() == null) {
            return 0;
        }
        return element.parent().children().size() - element.elementSiblingIndex();
    }

    @Override
    protected String getPseudoClass() {
        return "nth-last-child";
    }
}
