package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$15
extends HtmlTreeBuilderState {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isEndTag()) {
            Token.EndTag endTag = t.asEndTag();
            String name = endTag.normalName();
            if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellNames)) {
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    tb.transition(InRow);
                    return false;
                }
                tb.generateImpliedEndTags();
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                tb.clearFormattingElementsToLastMarker();
                tb.transition(InRow);
                return true;
            }
            if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellBody)) {
                tb.error(this);
                return false;
            }
            if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellTable)) return this.anythingElse(t, tb);
            if (!tb.inTableScope(name)) {
                tb.error(this);
                return false;
            }
            this.closeCell(tb);
            return tb.process(t);
        }
        if (!t.isStartTag()) return this.anythingElse(t, tb);
        if (!StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InCellCol)) return this.anythingElse(t, tb);
        if (!tb.inTableScope("td") && !tb.inTableScope("th")) {
            tb.error(this);
            return false;
        }
        this.closeCell(tb);
        return tb.process(t);
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        return tb.process(t, InBody);
    }

    private void closeCell(HtmlTreeBuilder tb) {
        if (tb.inTableScope("td")) {
            tb.processEndTag("td");
        } else {
            tb.processEndTag("th");
        }
    }
}
