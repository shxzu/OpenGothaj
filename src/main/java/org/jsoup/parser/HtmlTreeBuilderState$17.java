package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$17
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isStartTag() && StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InSelectTableEnd)) {
            tb.error(this);
            tb.popStackToClose("select");
            tb.resetInsertionMode();
            return tb.process(t);
        }
        if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.InSelectTableEnd)) {
            tb.error(this);
            if (tb.inTableScope(t.asEndTag().normalName())) {
                tb.popStackToClose("select");
                tb.resetInsertionMode();
                return tb.process(t);
            }
            return false;
        }
        return tb.process(t, InSelect);
    }
}
