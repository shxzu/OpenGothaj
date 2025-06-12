package org.jsoup.parser;

import java.util.ArrayList;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;
import org.jsoup.parser.Token;
import org.jsoup.parser.TokeniserState;

final class HtmlTreeBuilderState$7
extends HtmlTreeBuilderState {
    private static final int MaxStackScan = 24;

    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        switch (t.type) {
            case Character: {
                Token.Character c = t.asCharacter();
                if (c.getData().equals(nullString)) {
                    tb.error(this);
                    return false;
                }
                if (tb.framesetOk() && HtmlTreeBuilderState.isWhitespace(c)) {
                    tb.reconstructFormattingElements();
                    tb.insert(c);
                    break;
                }
                tb.reconstructFormattingElements();
                tb.insert(c);
                tb.framesetOk(false);
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
                return this.inBodyStartTag(t, tb);
            }
            case EndTag: {
                return this.inBodyEndTag(t, tb);
            }
            case EOF: {
                if (tb.templateModeSize() <= 0) break;
                return tb.process(t, InTemplate);
            }
        }
        return true;
    }

    private boolean inBodyStartTag(Token t, HtmlTreeBuilder tb) {
        String name;
        Token.StartTag startTag = t.asStartTag();
        switch (name = startTag.normalName()) {
            case "a": {
                if (tb.getActiveFormattingElement("a") != null) {
                    tb.error(this);
                    tb.processEndTag("a");
                    Element remainingA = tb.getFromStack("a");
                    if (remainingA != null) {
                        tb.removeFromActiveFormattingElements(remainingA);
                        tb.removeFromStack(remainingA);
                    }
                }
                tb.reconstructFormattingElements();
                Element el = tb.insert(startTag);
                tb.pushActiveFormattingElements(el);
                break;
            }
            case "span": {
                tb.reconstructFormattingElements();
                tb.insert(startTag);
                break;
            }
            case "li": {
                tb.framesetOk(false);
                ArrayList<Element> stack = tb.getStack();
                for (int i = stack.size() - 1; i > 0; --i) {
                    Element el = stack.get(i);
                    if (el.normalName().equals("li")) {
                        tb.processEndTag("li");
                        break;
                    }
                    if (tb.isSpecial(el) && !StringUtil.inSorted(el.normalName(), HtmlTreeBuilderState.Constants.InBodyStartLiBreakers)) break;
                }
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.insert(startTag);
                break;
            }
            case "html": {
                tb.error(this);
                if (tb.onStack("template")) {
                    return false;
                }
                ArrayList<Element> stack = tb.getStack();
                if (stack.size() <= 0) break;
                Element html = tb.getStack().get(0);
                if (!startTag.hasAttributes()) break;
                for (Attribute attribute : startTag.attributes) {
                    if (html.hasAttr(attribute.getKey())) continue;
                    html.attributes().put(attribute);
                }
                break;
            }
            case "body": {
                Element body;
                tb.error(this);
                ArrayList<Element> stack = tb.getStack();
                if (stack.size() == 1 || stack.size() > 2 && !stack.get(1).normalName().equals("body") || tb.onStack("template")) {
                    return false;
                }
                tb.framesetOk(false);
                if (!startTag.hasAttributes() || (body = tb.getFromStack("body")) == null) break;
                for (Attribute attribute : startTag.attributes) {
                    if (body.hasAttr(attribute.getKey())) continue;
                    body.attributes().put(attribute);
                }
                break;
            }
            case "frameset": {
                tb.error(this);
                ArrayList<Element> stack = tb.getStack();
                if (stack.size() == 1 || stack.size() > 2 && !stack.get(1).normalName().equals("body")) {
                    return false;
                }
                if (!tb.framesetOk()) {
                    return false;
                }
                Element second = stack.get(1);
                if (second.parent() != null) {
                    second.remove();
                }
                while (stack.size() > 1) {
                    stack.remove(stack.size() - 1);
                }
                tb.insert(startTag);
                tb.transition(InFrameset);
                break;
            }
            case "form": {
                if (tb.getFormElement() != null && !tb.onStack("template")) {
                    tb.error(this);
                    return false;
                }
                if (tb.inButtonScope("p")) {
                    tb.closeElement("p");
                }
                tb.insertForm(startTag, true, true);
                break;
            }
            case "plaintext": {
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.insert(startTag);
                tb.tokeniser.transition(TokeniserState.PLAINTEXT);
                break;
            }
            case "button": {
                if (tb.inButtonScope("button")) {
                    tb.error(this);
                    tb.processEndTag("button");
                    tb.process(startTag);
                    break;
                }
                tb.reconstructFormattingElements();
                tb.insert(startTag);
                tb.framesetOk(false);
                break;
            }
            case "nobr": {
                tb.reconstructFormattingElements();
                if (tb.inScope("nobr")) {
                    tb.error(this);
                    tb.processEndTag("nobr");
                    tb.reconstructFormattingElements();
                }
                Element el = tb.insert(startTag);
                tb.pushActiveFormattingElements(el);
                break;
            }
            case "table": {
                if (tb.getDocument().quirksMode() != Document.QuirksMode.quirks && tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.insert(startTag);
                tb.framesetOk(false);
                tb.transition(InTable);
                break;
            }
            case "input": {
                tb.reconstructFormattingElements();
                Element el = tb.insertEmpty(startTag);
                if (el.attr("type").equalsIgnoreCase("hidden")) break;
                tb.framesetOk(false);
                break;
            }
            case "hr": {
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.insertEmpty(startTag);
                tb.framesetOk(false);
                break;
            }
            case "image": {
                if (tb.getFromStack("svg") == null) {
                    return tb.process(startTag.name("img"));
                }
                tb.insert(startTag);
                break;
            }
            case "isindex": {
                FormElement form;
                tb.error(this);
                if (tb.getFormElement() != null) {
                    return false;
                }
                tb.processStartTag("form");
                if (startTag.hasAttribute("action") && (form = tb.getFormElement()) != null && startTag.hasAttribute("action")) {
                    String action = startTag.attributes.get("action");
                    form.attributes().put("action", action);
                }
                tb.processStartTag("hr");
                tb.processStartTag("label");
                String prompt = startTag.hasAttribute("prompt") ? startTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: ";
                tb.process(new Token.Character().data(prompt));
                Attributes inputAttribs = new Attributes();
                if (startTag.hasAttributes()) {
                    for (Attribute attr : startTag.attributes) {
                        if (StringUtil.inSorted(attr.getKey(), HtmlTreeBuilderState.Constants.InBodyStartInputAttribs)) continue;
                        inputAttribs.put(attr);
                    }
                }
                inputAttribs.put("name", "isindex");
                tb.processStartTag("input", inputAttribs);
                tb.processEndTag("label");
                tb.processStartTag("hr");
                tb.processEndTag("form");
                break;
            }
            case "textarea": {
                tb.insert(startTag);
                if (startTag.isSelfClosing()) break;
                tb.tokeniser.transition(TokeniserState.Rcdata);
                tb.markInsertionMode();
                tb.framesetOk(false);
                tb.transition(Text);
                break;
            }
            case "xmp": {
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.reconstructFormattingElements();
                tb.framesetOk(false);
                HtmlTreeBuilderState.handleRawtext(startTag, tb);
                break;
            }
            case "iframe": {
                tb.framesetOk(false);
                HtmlTreeBuilderState.handleRawtext(startTag, tb);
                break;
            }
            case "noembed": {
                HtmlTreeBuilderState.handleRawtext(startTag, tb);
                break;
            }
            case "select": {
                tb.reconstructFormattingElements();
                tb.insert(startTag);
                tb.framesetOk(false);
                if (startTag.selfClosing) break;
                HtmlTreeBuilderState state = tb.state();
                if (state.equals((Object)InTable) || state.equals((Object)InCaption) || state.equals((Object)InTableBody) || state.equals((Object)InRow) || state.equals((Object)InCell)) {
                    tb.transition(InSelectInTable);
                    break;
                }
                tb.transition(InSelect);
                break;
            }
            case "math": {
                tb.reconstructFormattingElements();
                tb.insert(startTag);
                break;
            }
            case "svg": {
                tb.reconstructFormattingElements();
                tb.insert(startTag);
                break;
            }
            case "h1": 
            case "h2": 
            case "h3": 
            case "h4": 
            case "h5": 
            case "h6": {
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                if (StringUtil.inSorted(tb.currentElement().normalName(), HtmlTreeBuilderState.Constants.Headings)) {
                    tb.error(this);
                    tb.pop();
                }
                tb.insert(startTag);
                break;
            }
            case "pre": 
            case "listing": {
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.insert(startTag);
                tb.reader.matchConsume("\n");
                tb.framesetOk(false);
                break;
            }
            case "dd": 
            case "dt": {
                tb.framesetOk(false);
                ArrayList<Element> stack = tb.getStack();
                int bottom = stack.size() - 1;
                int upper = bottom >= 24 ? bottom - 24 : 0;
                for (int i = bottom; i >= upper; --i) {
                    Element el = stack.get(i);
                    if (StringUtil.inSorted(el.normalName(), HtmlTreeBuilderState.Constants.DdDt)) {
                        tb.processEndTag(el.normalName());
                        break;
                    }
                    if (tb.isSpecial(el) && !StringUtil.inSorted(el.normalName(), HtmlTreeBuilderState.Constants.InBodyStartLiBreakers)) break;
                }
                if (tb.inButtonScope("p")) {
                    tb.processEndTag("p");
                }
                tb.insert(startTag);
                break;
            }
            case "optgroup": 
            case "option": {
                if (tb.currentElementIs("option")) {
                    tb.processEndTag("option");
                }
                tb.reconstructFormattingElements();
                tb.insert(startTag);
                break;
            }
            case "rp": 
            case "rt": {
                if (!tb.inScope("ruby")) break;
                tb.generateImpliedEndTags();
                if (!tb.currentElementIs("ruby")) {
                    tb.error(this);
                    tb.popStackToBefore("ruby");
                }
                tb.insert(startTag);
                break;
            }
            case "area": 
            case "br": 
            case "embed": 
            case "img": 
            case "keygen": 
            case "wbr": {
                tb.reconstructFormattingElements();
                tb.insertEmpty(startTag);
                tb.framesetOk(false);
                break;
            }
            case "b": 
            case "big": 
            case "code": 
            case "em": 
            case "font": 
            case "i": 
            case "s": 
            case "small": 
            case "strike": 
            case "strong": 
            case "tt": 
            case "u": {
                tb.reconstructFormattingElements();
                Element el = tb.insert(startTag);
                tb.pushActiveFormattingElements(el);
                break;
            }
            default: {
                if (!Tag.isKnownTag(name)) {
                    tb.insert(startTag);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartPClosers)) {
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insert(startTag);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartToHead)) {
                    return tb.process(t, InHead);
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartApplets)) {
                    tb.reconstructFormattingElements();
                    tb.insert(startTag);
                    tb.insertMarkerToFormattingElements();
                    tb.framesetOk(false);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartMedia)) {
                    tb.insertEmpty(startTag);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartDrop)) {
                    tb.error(this);
                    return false;
                }
                tb.reconstructFormattingElements();
                tb.insert(startTag);
            }
        }
        return true;
    }

    private boolean inBodyEndTag(Token t, HtmlTreeBuilder tb) {
        String name;
        Token.EndTag endTag = t.asEndTag();
        switch (name = endTag.normalName()) {
            case "template": {
                tb.process(t, InHead);
                break;
            }
            case "sarcasm": 
            case "span": {
                return this.anyOtherEndTag(t, tb);
            }
            case "li": {
                if (!tb.inListItemScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.generateImpliedEndTags(name);
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                break;
            }
            case "body": {
                if (!tb.inScope("body")) {
                    tb.error(this);
                    return false;
                }
                this.anyOtherEndTag(t, tb);
                tb.transition(AfterBody);
                break;
            }
            case "html": {
                boolean notIgnored = tb.processEndTag("body");
                if (!notIgnored) break;
                return tb.process(endTag);
            }
            case "form": {
                if (!tb.onStack("template")) {
                    FormElement currentForm = tb.getFormElement();
                    tb.setFormElement(null);
                    if (currentForm == null || !tb.inScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.generateImpliedEndTags();
                    if (!tb.currentElementIs(name)) {
                        tb.error(this);
                    }
                    tb.removeFromStack(currentForm);
                    break;
                }
                if (!tb.inScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.generateImpliedEndTags();
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                break;
            }
            case "p": {
                if (!tb.inButtonScope(name)) {
                    tb.error(this);
                    tb.processStartTag(name);
                    return tb.process(endTag);
                }
                tb.generateImpliedEndTags(name);
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                break;
            }
            case "dd": 
            case "dt": {
                if (!tb.inScope(name)) {
                    tb.error(this);
                    return false;
                }
                tb.generateImpliedEndTags(name);
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                break;
            }
            case "h1": 
            case "h2": 
            case "h3": 
            case "h4": 
            case "h5": 
            case "h6": {
                if (!tb.inScope(HtmlTreeBuilderState.Constants.Headings)) {
                    tb.error(this);
                    return false;
                }
                tb.generateImpliedEndTags(name);
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(HtmlTreeBuilderState.Constants.Headings);
                break;
            }
            case "br": {
                tb.error(this);
                tb.processStartTag("br");
                return false;
            }
            default: {
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyEndAdoptionFormatters)) {
                    return this.inBodyEndTagAdoption(t, tb);
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyEndClosers)) {
                    if (!tb.inScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.generateImpliedEndTags();
                    if (!tb.currentElementIs(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    break;
                }
                if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartApplets)) {
                    if (tb.inScope("name")) break;
                    if (!tb.inScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    tb.generateImpliedEndTags();
                    if (!tb.currentElementIs(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    tb.clearFormattingElementsToLastMarker();
                    break;
                }
                return this.anyOtherEndTag(t, tb);
            }
        }
        return true;
    }

    boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
        String name = t.asEndTag().normalName;
        ArrayList<Element> stack = tb.getStack();
        Element elFromStack = tb.getFromStack(name);
        if (elFromStack == null) {
            tb.error(this);
            return false;
        }
        for (int pos = stack.size() - 1; pos >= 0; --pos) {
            Element node = stack.get(pos);
            if (node.normalName().equals(name)) {
                tb.generateImpliedEndTags(name);
                if (!tb.currentElementIs(name)) {
                    tb.error(this);
                }
                tb.popStackToClose(name);
                break;
            }
            if (!tb.isSpecial(node)) continue;
            tb.error(this);
            return false;
        }
        return true;
    }

    private boolean inBodyEndTagAdoption(Token t, HtmlTreeBuilder tb) {
        Token.EndTag endTag = t.asEndTag();
        String name = endTag.normalName();
        ArrayList<Element> stack = tb.getStack();
        for (int i = 0; i < 8; ++i) {
            Element formatEl = tb.getActiveFormattingElement(name);
            if (formatEl == null) {
                return this.anyOtherEndTag(t, tb);
            }
            if (!tb.onStack(formatEl)) {
                tb.error(this);
                tb.removeFromActiveFormattingElements(formatEl);
                return true;
            }
            if (!tb.inScope(formatEl.normalName())) {
                tb.error(this);
                return false;
            }
            if (tb.currentElement() != formatEl) {
                tb.error(this);
            }
            Element furthestBlock = null;
            Element commonAncestor = null;
            boolean seenFormattingElement = false;
            int stackSize = stack.size();
            int bookmark = -1;
            for (int si = 1; si < stackSize && si < 64; ++si) {
                Element el = stack.get(si);
                if (el == formatEl) {
                    commonAncestor = stack.get(si - 1);
                    seenFormattingElement = true;
                    bookmark = tb.positionOfElement(el);
                    continue;
                }
                if (!seenFormattingElement || !tb.isSpecial(el)) continue;
                furthestBlock = el;
                break;
            }
            if (furthestBlock == null) {
                tb.popStackToClose(formatEl.normalName());
                tb.removeFromActiveFormattingElements(formatEl);
                return true;
            }
            Element node = furthestBlock;
            Element lastNode = furthestBlock;
            for (int j = 0; j < 3; ++j) {
                if (tb.onStack(node)) {
                    node = tb.aboveOnStack(node);
                }
                if (!tb.isInActiveFormattingElements(node)) {
                    tb.removeFromStack(node);
                    continue;
                }
                if (node == formatEl) break;
                Element replacement = new Element(tb.tagFor(node.nodeName(), ParseSettings.preserveCase), tb.getBaseUri());
                tb.replaceActiveFormattingElement(node, replacement);
                tb.replaceOnStack(node, replacement);
                node = replacement;
                if (lastNode == furthestBlock) {
                    bookmark = tb.positionOfElement(node) + 1;
                }
                if (lastNode.parent() != null) {
                    lastNode.remove();
                }
                node.appendChild(lastNode);
                lastNode = node;
            }
            if (commonAncestor != null) {
                if (StringUtil.inSorted(commonAncestor.normalName(), HtmlTreeBuilderState.Constants.InBodyEndTableFosters)) {
                    if (lastNode.parent() != null) {
                        lastNode.remove();
                    }
                    tb.insertInFosterParent(lastNode);
                } else {
                    if (lastNode.parent() != null) {
                        lastNode.remove();
                    }
                    commonAncestor.appendChild(lastNode);
                }
            }
            Element adopter = new Element(formatEl.tag(), tb.getBaseUri());
            adopter.attributes().addAll(formatEl.attributes());
            adopter.appendChildren(furthestBlock.childNodes());
            furthestBlock.appendChild(adopter);
            tb.removeFromActiveFormattingElements(formatEl);
            tb.pushWithBookmark(adopter, bookmark);
            tb.removeFromStack(formatEl);
            tb.insertOnStackAfter(furthestBlock, adopter);
        }
        return true;
    }
}
