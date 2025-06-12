package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$3
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
        } else if (t.isComment()) {
            tb.insert(t.asComment());
        } else {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return InBody.process(t, tb);
            }
            if (t.isStartTag() && t.asStartTag().normalName().equals("head")) {
                Element head = tb.insert(t.asStartTag());
                tb.setHeadElement(head);
                tb.transition(InHead);
            } else {
                if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.BeforeHtmlToHead)) {
                    tb.processStartTag("head");
                    return tb.process(t);
                }
                if (t.isEndTag()) {
                    tb.error(this);
                    return false;
                }
                tb.processStartTag("head");
                return tb.process(t);
            }
        }
        return true;
    }
}
