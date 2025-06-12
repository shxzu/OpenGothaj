package org.jsoup.parser;

import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$20
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
            if (t.isStartTag()) {
                Token.StartTag start = t.asStartTag();
                switch (start.normalName()) {
                    case "html": {
                        return tb.process(start, InBody);
                    }
                    case "frameset": {
                        tb.insert(start);
                        break;
                    }
                    case "frame": {
                        tb.insertEmpty(start);
                        break;
                    }
                    case "noframes": {
                        return tb.process(start, InHead);
                    }
                    default: {
                        tb.error(this);
                        return false;
                    }
                }
            } else if (t.isEndTag() && t.asEndTag().normalName().equals("frameset")) {
                if (tb.currentElementIs("html")) {
                    tb.error(this);
                    return false;
                }
                tb.pop();
                if (!tb.isFragmentParsing() && !tb.currentElementIs("frameset")) {
                    tb.transition(AfterFrameset);
                }
            } else if (t.isEOF()) {
                if (!tb.currentElementIs("html")) {
                    tb.error(this);
                    return true;
                }
            } else {
                tb.error(this);
                return false;
            }
        }
        return true;
    }
}
