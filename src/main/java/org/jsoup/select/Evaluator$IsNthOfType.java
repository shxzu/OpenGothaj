package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

public class Evaluator$IsNthOfType
extends Evaluator.CssNthEvaluator {
    public Evaluator$IsNthOfType(int a, int b) {
        super(a, b);
    }

    @Override
    protected int calculatePosition(Element root, Element element) {
        int pos = 0;
        if (element.parent() == null) {
            return 0;
        }
        Elements family = element.parent().children();
        for (Element el : family) {
            if (el.tag().equals(element.tag())) {
                ++pos;
            }
            if (el != element) continue;
            break;
        }
        return pos;
    }

    @Override
    protected String getPseudoClass() {
        return "nth-of-type";
    }
}
