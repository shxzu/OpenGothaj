package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$2
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isDoctype()) {
            tb.error(this);
            return false;
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
        } else if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
        } else if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
            tb.insert(t.asStartTag());
            tb.transition(BeforeHead);
        } else {
            if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.BeforeHtmlToHead)) {
                return this.anythingElse(t, tb);
            }
            if (t.isEndTag()) {
                tb.error(this);
                return false;
            }
            return this.anythingElse(t, tb);
        }
        return true;
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        tb.insertStartTag("html");
        tb.transition(BeforeHead);
        return tb.process(t);
    }
}
