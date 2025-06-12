package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$11
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isEndTag() && t.asEndTag().normalName().equals("caption")) {
            Token.EndTag endTag = t.asEndTag();
            String name = endTag.normalName();
            if (!tb.inTableScope(name)) {
                tb.error(this);
                return false;
            }
            tb.generateImpliedEndTags();
            if (!tb.currentElementIs("caption")) {
                tb.error(this);
            }
            tb.popStackToClose("caption");
            tb.clearFormattingElementsToLastMarker();
            tb.transition(InTable);
        } else if (t.isStartTag() && StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InCellCol) || t.isEndTag() && t.asEndTag().normalName().equals("table")) {
            tb.error(this);
            boolean processed = tb.processEndTag("caption");
            if (processed) {
                return tb.process(t);
            }
        } else {
            if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.InCaptionIgnore)) {
                tb.error(this);
                return false;
            }
            return tb.process(t, InBody);
        }
        return true;
    }
}
