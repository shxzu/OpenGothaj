package org.jsoup.parser;

import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$23
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isComment()) {
            tb.insert(t.asComment());
        } else {
            if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return tb.process(t, InBody);
            }
            if (!t.isEOF()) {
                if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                    return tb.process(t, InHead);
                }
                tb.error(this);
                return false;
            }
        }
        return true;
    }
}
