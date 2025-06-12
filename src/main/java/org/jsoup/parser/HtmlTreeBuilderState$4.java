package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;
import org.jsoup.parser.TokeniserState;
import org.jsoup.parser.TreeBuilder;

final class HtmlTreeBuilderState$4
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
            return true;
        }
        switch (t.type) {
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
                    return InBody.process(t, tb);
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InHeadEmpty)) {
                    Element el = tb.insertEmpty(start);
                    if (!name.equals("base") || !el.hasAttr("href")) break;
                    tb.maybeSetBaseUri(el);
                    break;
                }
                if (name.equals("meta")) {
                    tb.insertEmpty(start);
                    break;
                }
                if (name.equals("title")) {
                    HtmlTreeBuilderState.handleRcData(start, tb);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InHeadRaw)) {
                    HtmlTreeBuilderState.handleRawtext(start, tb);
                    break;
                }
                if (name.equals("noscript")) {
                    tb.insert(start);
                    tb.transition(InHeadNoscript);
                    break;
                }
                if (name.equals("script")) {
                    tb.tokeniser.transition(TokeniserState.ScriptData);
                    tb.markInsertionMode();
                    tb.transition(Text);
                    tb.insert(start);
                    break;
                }
                if (name.equals("head")) {
                    tb.error(this);
                    return false;
                }
                if (name.equals("template")) {
                    tb.insert(start);
                    tb.insertMarkerToFormattingElements();
                    tb.framesetOk(false);
                    tb.transition(InTemplate);
                    tb.pushTemplateMode(InTemplate);
                    break;
                }
                return this.anythingElse(t, tb);
            }
            case EndTag: {
                Token.EndTag end = t.asEndTag();
                String name = end.normalName();
                if (name.equals("head")) {
                    tb.pop();
                    tb.transition(AfterHead);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InHeadEnd)) {
                    return this.anythingElse(t, tb);
                }
                if (name.equals("template")) {
                    if (!tb.onStack(name)) {
                        tb.error(this);
                        break;
                    }
                    tb.generateImpliedEndTags(true);
                    if (!name.equals(tb.currentElement().normalName())) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    tb.clearFormattingElementsToLastMarker();
                    tb.popTemplateMode();
                    tb.resetInsertionMode();
                    break;
                }
                tb.error(this);
                return false;
            }
            default: {
                return this.anythingElse(t, tb);
            }
        }
        return true;
    }

    private boolean anythingElse(Token t, TreeBuilder tb) {
        tb.processEndTag("head");
        return tb.process(t);
    }
}
