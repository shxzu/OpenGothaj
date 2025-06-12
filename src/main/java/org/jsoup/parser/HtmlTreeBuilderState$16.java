package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$16
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        block0 : switch (t.type) {
            case Character: {
                Token.Character c = t.asCharacter();
                if (c.getData().equals(nullString)) {
                    tb.error(this);
                    return false;
                }
                tb.insert(c);
                break;
            }
            case Comment: {
                tb.insert(t.asComment());
                break;
            }
            case Doctype: {
                tb.error(this);
                return false;
            }
            case StartTag: {
                Token.StartTag start = t.asStartTag();
                String name = start.normalName();
                if (name.equals("html")) {
                    return tb.process(start, InBody);
                }
                if (name.equals("option")) {
                    if (tb.currentElementIs("option")) {
                        tb.processEndTag("option");
                    }
                    tb.insert(start);
                    break;
                }
                if (name.equals("optgroup")) {
                    if (tb.currentElementIs("option")) {
                        tb.processEndTag("option");
                    }
                    if (tb.currentElementIs("optgroup")) {
                        tb.processEndTag("optgroup");
                    }
                    tb.insert(start);
                    break;
                }
                if (name.equals("select")) {
                    tb.error(this);
                    return tb.processEndTag("select");
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InSelectEnd)) {
                    tb.error(this);
                    if (!tb.inSelectScope("select")) {
                        return false;
                    }
                    tb.processEndTag("select");
                    return tb.process(start);
                }
                if (name.equals("script") || name.equals("template")) {
                    return tb.process(t, InHead);
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                String name;
                Token.EndTag end = t.asEndTag();
                switch (name = end.normalName()) {
                    case "optgroup": {
                        if (tb.currentElementIs("option") && tb.aboveOnStack(tb.currentElement()) != null && tb.aboveOnStack(tb.currentElement()).normalName().equals("optgroup")) {
                            tb.processEndTag("option");
                        }
                        if (tb.currentElementIs("optgroup")) {
                            tb.pop();
                            break block0;
                        }
                        tb.error(this);
                        break block0;
                    }
                    case "option": {
                        if (tb.currentElementIs("option")) {
                            tb.pop();
                            break block0;
                        }
                        tb.error(this);
                        break block0;
                    }
                    case "select": {
                        if (!tb.inSelectScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.popStackToClose(name);
                        tb.resetInsertionMode();
                        break block0;
                    }
                    case "template": {
                        return tb.process(t, InHead);
                    }
                }
                return this.anythingElse(t, tb);
            }
            case EOF: {
                if (tb.currentElementIs("html")) break;
                tb.error(this);
                break;
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }

    private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        tb.error(this);
        return false;
    }
}
