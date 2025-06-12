package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$6
extends HtmlTreeBuilderState {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
            return true;
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
            return true;
        }
        if (t.isDoctype()) {
            tb.error(this);
            return true;
        }
        if (t.isStartTag()) {
            Token.StartTag startTag = t.asStartTag();
            String name = startTag.normalName();
            if (name.equals("html")) {
                return tb.process(t, InBody);
            }
            if (name.equals("body")) {
                tb.insert(startTag);
                tb.framesetOk(false);
                tb.transition(InBody);
                return true;
            }
            if (name.equals("frameset")) {
                tb.insert(startTag);
                tb.transition(InFrameset);
                return true;
            }
            if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartToHead)) {
                tb.error(this);
                Element head = tb.getHeadElement();
                tb.push(head);
                tb.process(t, InHead);
                tb.removeFromStack(head);
                return true;
            }
            if (name.equals("head")) {
                tb.error(this);
                return false;
            }
            this.anythingElse(t, tb);
            return true;
        }
        if (!t.isEndTag()) {
            this.anythingElse(t, tb);
            return true;
        }
        String name = t.asEndTag().normalName();
        if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.AfterHeadBody)) {
            this.anythingElse(t, tb);
            return true;
        }
        if (name.equals("template")) {
            tb.process(t, InHead);
            return true;
        }
        tb.error(this);
        return false;
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        tb.processStartTag("body");
        tb.framesetOk(true);
        return tb.process(t);
    }
}
