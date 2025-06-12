package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$9
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        if (t.isCharacter() && StringUtil.inSorted(tb.currentElement().normalName(), HtmlTreeBuilderState.Constants.InTableFoster)) {
            tb.newPendingTableCharacters();
            tb.markInsertionMode();
            tb.transition(InTableText);
            return tb.process(t);
        }
        if (t.isComment()) {
            tb.insert(t.asComment());
            return true;
        }
        if (t.isDoctype()) {
            tb.error(this);
            return false;
        }
        if (t.isStartTag()) {
            Token.StartTag startTag = t.asStartTag();
            String name = startTag.normalName();
            if (name.equals("caption")) {
                tb.clearStackToTableContext();
                tb.insertMarkerToFormattingElements();
                tb.insert(startTag);
                tb.transition(InCaption);
            } else if (name.equals("colgroup")) {
                tb.clearStackToTableContext();
                tb.insert(startTag);
                tb.transition(InColumnGroup);
            } else {
                if (name.equals("col")) {
                    tb.clearStackToTableContext();
                    tb.processStartTag("colgroup");
                    return tb.process(t);
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableToBody)) {
                    tb.clearStackToTableContext();
                    tb.insert(startTag);
                    tb.transition(InTableBody);
                } else {
                    if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableAddBody)) {
                        tb.clearStackToTableContext();
                        tb.processStartTag("tbody");
                        return tb.process(t);
                    }
                    if (name.equals("table")) {
                        tb.error(this);
                        if (!tb.inTableScope(name)) {
                            return false;
                        }
                        tb.popStackToClose(name);
                        if (!tb.resetInsertionMode()) {
                            tb.insert(startTag);
                            return true;
                        }
                        return tb.process(t);
                    }
                    if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableToHead)) {
                        return tb.process(t, InHead);
                    }
                    if (name.equals("input")) {
                        if (!startTag.hasAttributes() || !startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                            return this.anythingElse(t, tb);
                        }
                        tb.insertEmpty(startTag);
                    } else if (name.equals("form")) {
                        tb.error(this);
                        if (tb.getFormElement() != null || tb.onStack("template")) {
                            return false;
                        }
                        tb.insertForm(startTag, false, false);
                    } else {
                        return this.anythingElse(t, tb);
                    }
                }
            }
            return true;
        }
        if (t.isEndTag()) {
            Token.EndTag endTag = t.asEndTag();
            String name = endTag.normalName();
            if (name.equals("table")) {
                if (!tb.inTableScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.popStackToClose("table");
                tb.resetInsertionMode();
            } else {
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableEndErr)) {
                    tb.error(this);
                    return false;
                }
                if (name.equals("template")) {
                    tb.process(t, InHead);
                } else {
                    return this.anythingElse(t, tb);
                }
            }
            return true;
        }
        if (t.isEOF()) {
            if (tb.currentElementIs("html")) {
                tb.error(this);
            }
            return true;
        }
        return this.anythingElse(t, tb);
    }

    boolean anythingElse(Token t, HtmlTreeBuilder tb) {
        tb.error(this);
        tb.setFosterInserts(true);
        tb.process(t, InBody);
        tb.setFosterInserts(false);
        return true;
    }
}
