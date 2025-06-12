package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;
import org.jsoup.parser.TreeBuilder;

final class HtmlTreeBuilderState$14
extends HtmlTreeBuilderState {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isStartTag()) {
            Token.StartTag startTag = t.asStartTag();
            String name = startTag.normalName();
            if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellNames)) {
                tb.clearStackToTableRowContext();
                tb.insert(startTag);
                tb.transition(InCell);
                tb.insertMarkerToFormattingElements();
                return true;
            }
            if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InRowMissing)) return this.anythingElse(t, tb);
            return this.handleMissingTr(t, tb);
        }
        if (!t.isEndTag()) return this.anythingElse(t, tb);
        Token.EndTag endTag = t.asEndTag();
        String name = endTag.normalName();
        if (name.equals("tr")) {
            if (!tb.inTableScope(name)) {
                tb.error(this);
                return false;
            }
            tb.clearStackToTableRowContext();
            tb.pop();
            tb.transition(InTableBody);
            return true;
        }
        if (name.equals("table")) {
            return this.handleMissingTr(t, tb);
        }
        if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableToBody)) {
            if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InRowIgnore)) return this.anythingElse(t, tb);
            tb.error(this);
            return false;
        }
        if (tb.inTableScope(name) && tb.inTableScope("tr")) {
            tb.clearStackToTableRowContext();
            tb.pop();
            tb.transition(InTableBody);
            return true;
        }
        tb.error(this);
        return false;
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        return tb.process(t, InTable);
    }

    private boolean handleMissingTr(Token t, TreeBuilder tb) {
        boolean processed = tb.processEndTag("tr");
        if (processed) {
            return tb.process(t);
        }
        return false;
    }
}
