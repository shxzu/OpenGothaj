package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$13
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        switch (t.type) {
            case StartTag: {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.normalName();
                if (name.equals("tr")) {
                    tb.clearStackToTableBodyContext();
                    tb.insert(startTag);
                    tb.transition(InRow);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellNames)) {
                    tb.error(this);
                    tb.processStartTag("tr");
                    return tb.process(startTag);
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableBodyExit)) {
                    return this.exitTableBody(t, tb);
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                Token.EndTag endTag = t.asEndTag();
                String name = endTag.normalName();
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableEndIgnore)) {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.clearStackToTableBodyContext();
                    tb.pop();
                    tb.transition(InTable);
                    break;
                }
                if (name.equals("table")) {
                    return this.exitTableBody(t, tb);
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableBodyEndIgnore)) {
                    tb.error(this);
                    return false;
                }
                return this.anythingElse(t, tb);
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }

    private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
        if (!(tb.inTableScope("tbody") || tb.inTableScope("thead") || tb.inScope("tfoot"))) {
            tb.error(this);
            return false;
        }
        tb.clearStackToTableBodyContext();
        tb.processEndTag(tb.currentElement().normalName());
        return tb.process(t);
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        return tb.process(t, InTable);
    }
}
