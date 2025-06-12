package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

public final class Evaluator$IsNthChild
extends Evaluator.CssNthEvaluator {
    public Evaluator$IsNthChild(int a, int b) {
        super(a, b);
    }

    @Override
    protected int calculatePosition(Element root, Element element) {
        return element.elementSiblingIndex() + 1;
    }

    @Override
    protected String getPseudoClass() {
        return "nth-child";
    }
}
