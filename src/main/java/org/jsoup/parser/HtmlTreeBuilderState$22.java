package org.jsoup.parser;

import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$22
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isComment()) {
            tb.insert(t.asComment());
        } else {
            if (t.isDoctype() || t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return tb.process(t, InBody);
            }
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (!t.isEOF()) {
                tb.error(this);
                tb.resetBody();
                return tb.process(t);
            }
        }
        return true;
    }
}
