package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$5
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isDoctype()) {
            tb.error(this);
        } else {
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
                return tb.process(t, InBody);
            }
            if (t.isEndTag() && t.asEndTag().normalName().equals("noscript")) {
                tb.pop();
                tb.transition(InHead);
            } else {
                if (HtmlTreeBuilderState.isWhitespace(t) || t.isComment() || t.isStartTag() && StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InHeadNoScriptHead)) {
                    return tb.process(t, InHead);
                }
                if (t.isEndTag() && t.asEndTag().normalName().equals("br")) {
                    return this.anythingElse(t, tb);
                }
                if (t.isStartTag() && StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InHeadNoscriptIgnore) || t.isEndTag()) {
                    tb.error(this);
                    return false;
                }
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        tb.error(this);
        tb.insert(new Token.Character().data(t.toString()));
        return true;
    }
}
