package org.jsoup.parser;

import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$21
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
                return tb.process(t, InBody);
            }
            if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
                tb.transition(AfterAfterFrameset);
            } else {
                if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                    return tb.process(t, InHead);
                }
                if (!t.isEOF()) {
                    tb.error(this);
                    return false;
                }
            }
        }
        return true;
    }
}
