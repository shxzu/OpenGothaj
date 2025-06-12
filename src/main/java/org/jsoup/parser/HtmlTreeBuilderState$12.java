package org.jsoup.parser;

import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$12
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
            return true;
        }
        block0 : switch (t.type) {
            case Comment: {
                tb.insert(t.asComment());
                break;
            }
            case Doctype: {
                tb.error(this);
                break;
            }
            case StartTag: {
                Token.StartTag startTag = t.asStartTag();
                switch (startTag.normalName()) {
                    case "html": {
                        return tb.process(t, InBody);
                    }
                    case "col": {
                        tb.insertEmpty(startTag);
                        break block0;
                    }
                    case "template": {
                        tb.process(t, InHead);
                        break block0;
                    }
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                String name;
                Token.EndTag endTag = t.asEndTag();
                switch (name = endTag.normalName()) {
                    case "colgroup": {
                        if (!tb.currentElementIs(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.pop();
                        tb.transition(InTable);
                        break block0;
                    }
                    case "template": {
                        tb.process(t, InHead);
                        break block0;
                    }
                }
                return this.anythingElse(t, tb);
            }
            case EOF: {
                if (tb.currentElementIs("html")) {
                    return true;
                }
                return this.anythingElse(t, tb);
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        if (!tb.currentElementIs("colgroup")) {
            tb.error(this);
            return false;
        }
        tb.pop();
        tb.transition(InTable);
        tb.process(t);
        return true;
    }
}
