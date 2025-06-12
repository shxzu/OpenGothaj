package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$18
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        switch (t.type) {
            case Comment: 
            case Doctype: 
            case Character: {
                tb.process(t, InBody);
                break;
            }
            case StartTag: {
                String name = t.asStartTag().normalName();
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTemplateToHead)) {
                    tb.process(t, InHead);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTemplateToTable)) {
                    tb.popTemplateMode();
                    tb.pushTemplateMode(InTable);
                    tb.transition(InTable);
                    return tb.process(t);
                }
                if (name.equals("col")) {
                    tb.popTemplateMode();
                    tb.pushTemplateMode(InColumnGroup);
                    tb.transition(InColumnGroup);
                    return tb.process(t);
                }
                if (name.equals("tr")) {
                    tb.popTemplateMode();
                    tb.pushTemplateMode(InTableBody);
                    tb.transition(InTableBody);
                    return tb.process(t);
                }
                if (name.equals("td") || name.equals("th")) {
                    tb.popTemplateMode();
                    tb.pushTemplateMode(InRow);
                    tb.transition(InRow);
                    return tb.process(t);
                }
                tb.popTemplateMode();
                tb.pushTemplateMode(InBody);
                tb.transition(InBody);
                return tb.process(t);
            }
            case EndTag: {
                String name = t.asEndTag().normalName();
                if (name.equals("template")) {
                    tb.process(t, InHead);
                    break;
                }
                tb.error(this);
                return false;
            }
            case EOF: {
                if (!tb.onStack("template")) {
                    return true;
                }
                tb.error(this);
                tb.popStackToClose("template");
                tb.clearFormattingElementsToLastMarker();
                tb.popTemplateMode();
                tb.resetInsertionMode();
                if (tb.state() != InTemplate && tb.templateModeSize() < 12) {
                    return tb.process(t);
                }
                return true;
            }
        }
        return true;
    }
}
