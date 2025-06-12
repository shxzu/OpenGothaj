package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

public class Evaluator$IsNthLastOfType
extends Evaluator.CssNthEvaluator {
    public Evaluator$IsNthLastOfType(int a, int b) {
        super(a, b);
    }

    @Override
    protected int calculatePosition(Element root, Element element) {
        int pos = 0;
        if (element.parent() == null) {
            return 0;
        }
        Elements family = element.parent().children();
        for (int i = element.elementSiblingIndex(); i < family.size(); ++i) {
            if (!((Element)family.get(i)).tag().equals(element.tag())) continue;
            ++pos;
        }
        return pos;
    }

    @Override
    protected String getPseudoClass() {
        return "nth-last-of-type";
    }
}
