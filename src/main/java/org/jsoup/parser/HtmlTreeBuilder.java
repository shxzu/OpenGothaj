package org.jsoup.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.ParseError;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.parser.Token;
import org.jsoup.parser.TokeniserState;
import org.jsoup.parser.TreeBuilder;

public class HtmlTreeBuilder
extends TreeBuilder {
    static final String[] TagsSearchInScope = new String[]{"applet", "caption", "html", "marquee", "object", "table", "td", "th"};
    static final String[] TagSearchList = new String[]{"ol", "ul"};
    static final String[] TagSearchButton = new String[]{"button"};
    static final String[] TagSearchTableScope = new String[]{"html", "table"};
    static final String[] TagSearchSelectScope = new String[]{"optgroup", "option"};
    static final String[] TagSearchEndTags = new String[]{"dd", "dt", "li", "optgroup", "option", "p", "rb", "rp", "rt", "rtc"};
    static final String[] TagThoroughSearchEndTags = new String[]{"caption", "colgroup", "dd", "dt", "li", "optgroup", "option", "p", "rb", "rp", "rt", "rtc", "tbody", "td", "tfoot", "th", "thead", "tr"};
    static final String[] TagSearchSpecial = new String[]{"address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption", "center", "col", "colgroup", "command", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing", "marquee", "menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section", "select", "style", "summary", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "title", "tr", "ul", "wbr", "xmp"};
    public static final int MaxScopeSearchDepth = 100;
    private HtmlTreeBuilderState state;
    private HtmlTreeBuilderState originalState;
    private boolean baseUriSetFromDoc;
    @Nullable
    private Element headElement;
    @Nullable
    private FormElement formElement;
    @Nullable
    private Element contextElement;
    private ArrayList<Element> formattingElements;
    private ArrayList<HtmlTreeBuilderState> tmplInsertMode;
    private List<String> pendingTableCharacters;
    private Token.EndTag emptyEnd;
    private boolean framesetOk;
    private boolean fosterInserts;
    private boolean fragmentParsing;
    private static final int maxQueueDepth = 256;
    private String[] specificScopeTarget = new String[]{null};
    private static final int maxUsedFormattingElements = 12;

    @Override
    ParseSettings defaultSettings() {
        return ParseSettings.htmlDefault;
    }

    @Override
    HtmlTreeBuilder newInstance() {
        return new HtmlTreeBuilder();
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void initialiseParse(Reader input, String baseUri, Parser parser) {
        super.initialiseParse(input, baseUri, parser);
        this.state = HtmlTreeBuilderState.Initial;
        this.originalState = null;
        this.baseUriSetFromDoc = false;
        this.headElement = null;
        this.formElement = null;
        this.contextElement = null;
        this.formattingElements = new ArrayList();
        this.tmplInsertMode = new ArrayList();
        this.pendingTableCharacters = new ArrayList<String>();
        this.emptyEnd = new Token.EndTag();
        this.framesetOk = true;
        this.fosterInserts = false;
        this.fragmentParsing = false;
    }

    @Override
    List<Node> parseFragment(String inputFragment, @Nullable Element context, String baseUri, Parser parser) {
        this.state = HtmlTreeBuilderState.Initial;
        this.initialiseParse(new StringReader(inputFragment), baseUri, parser);
        this.contextElement = context;
        this.fragmentParsing = true;
        Node root = null;
        if (context != null) {
            String contextTag;
            if (context.ownerDocument() != null) {
                this.doc.quirksMode(context.ownerDocument().quirksMode());
            }
            switch (contextTag = context.normalName()) {
                case "title": 
                case "textarea": {
                    this.tokeniser.transition(TokeniserState.Rcdata);
                    break;
                }
                case "iframe": 
                case "noembed": 
                case "noframes": 
                case "style": 
                case "xml": {
                    this.tokeniser.transition(TokeniserState.Rawtext);
                    break;
                }
                case "script": {
                    this.tokeniser.transition(TokeniserState.ScriptData);
                    break;
                }
                case "noscript": {
                    this.tokeniser.transition(TokeniserState.Data);
                    break;
                }
                case "plaintext": {
                    this.tokeniser.transition(TokeniserState.PLAINTEXT);
                    break;
                }
                case "template": {
                    this.tokeniser.transition(TokeniserState.Data);
                    this.pushTemplateMode(HtmlTreeBuilderState.InTemplate);
                    break;
                }
                default: {
                    this.tokeniser.transition(TokeniserState.Data);
                }
            }
            root = new Element(this.tagFor(contextTag, this.settings), baseUri);
            this.doc.appendChild(root);
            this.stack.add(root);
            this.resetInsertionMode();
            for (Element formSearch = context; formSearch != null; formSearch = formSearch.parent()) {
                if (!(formSearch instanceof FormElement)) continue;
                this.formElement = (FormElement)formSearch;
                break;
            }
        }
        this.runParser();
        if (context != null) {
            List<Node> nodes = root.siblingNodes();
            if (!nodes.isEmpty()) {
                ((Element)root).insertChildren(-1, nodes);
            }
            return root.childNodes();
        }
        return this.doc.childNodes();
    }

    @Override
    protected boolean process(Token token) {
        this.currentToken = token;
        return this.state.process(token, this);
    }

    boolean process(Token token, HtmlTreeBuilderState state) {
        this.currentToken = token;
        return state.process(token, this);
    }

    void transition(HtmlTreeBuilderState state) {
        this.state = state;
    }

    HtmlTreeBuilderState state() {
        return this.state;
    }

    void markInsertionMode() {
        this.originalState = this.state;
    }

    HtmlTreeBuilderState originalState() {
        return this.originalState;
    }

    void framesetOk(boolean framesetOk) {
        this.framesetOk = framesetOk;
    }

    boolean framesetOk() {
        return this.framesetOk;
    }

    Document getDocument() {
        return this.doc;
    }

    String getBaseUri() {
        return this.baseUri;
    }

    void maybeSetBaseUri(Element base) {
        if (this.baseUriSetFromDoc) {
            return;
        }
        String href = base.absUrl("href");
        if (href.length() != 0) {
            this.baseUri = href;
            this.baseUriSetFromDoc = true;
            this.doc.setBaseUri(href);
        }
    }

    boolean isFragmentParsing() {
        return this.fragmentParsing;
    }

    void error(HtmlTreeBuilderState state) {
        if (this.parser.getErrors().canAddError()) {
            this.parser.getErrors().add(new ParseError(this.reader, "Unexpected %s token [%s] when in state [%s]", new Object[]{this.currentToken.tokenType(), this.currentToken, state}));
        }
    }

    Element insert(Token.StartTag startTag) {
        int dupes;
        if (startTag.hasAttributes() && !startTag.attributes.isEmpty() && (dupes = startTag.attributes.deduplicate(this.settings)) > 0) {
            this.error("Dropped duplicate attribute(s) in tag [%s]", startTag.normalName);
        }
        if (startTag.isSelfClosing()) {
            Element el = this.insertEmpty(startTag);
            this.stack.add(el);
            this.tokeniser.transition(TokeniserState.Data);
            this.tokeniser.emit(this.emptyEnd.reset().name(el.tagName()));
            return el;
        }
        Element el = new Element(this.tagFor(startTag.name(), this.settings), null, this.settings.normalizeAttributes(startTag.attributes));
        this.insert(el, startTag);
        return el;
    }

    Element insertStartTag(String startTagName) {
        Element el = new Element(this.tagFor(startTagName, this.settings), null);
        this.insert(el);
        return el;
    }

    void insert(Element el) {
        this.insertNode(el, null);
        this.stack.add(el);
    }

    private void insert(Element el, @Nullable Token token) {
        this.insertNode(el, token);
        this.stack.add(el);
    }

    Element insertEmpty(Token.StartTag startTag) {
        Tag tag = this.tagFor(startTag.name(), this.settings);
        Element el = new Element(tag, null, this.settings.normalizeAttributes(startTag.attributes));
        this.insertNode(el, startTag);
        if (startTag.isSelfClosing()) {
            if (tag.isKnownTag()) {
                if (!tag.isEmpty()) {
                    this.tokeniser.error("Tag [%s] cannot be self closing; not a void tag", tag.normalName());
                }
            } else {
                tag.setSelfClosing();
            }
        }
        return el;
    }

    FormElement insertForm(Token.StartTag startTag, boolean onStack, boolean checkTemplateStack) {
        Tag tag = this.tagFor(startTag.name(), this.settings);
        FormElement el = new FormElement(tag, null, this.settings.normalizeAttributes(startTag.attributes));
        if (checkTemplateStack) {
            if (!this.onStack("template")) {
                this.setFormElement(el);
            }
        } else {
            this.setFormElement(el);
        }
        this.insertNode(el, startTag);
        if (onStack) {
            this.stack.add(el);
        }
        return el;
    }

    void insert(Token.Comment commentToken) {
        Comment comment = new Comment(commentToken.getData());
        this.insertNode(comment, commentToken);
    }

    void insert(Token.Character characterToken) {
        Element el = this.currentElement();
        String tagName = el.normalName();
        String data = characterToken.getData();
        LeafNode node = characterToken.isCData() ? new CDataNode(data) : (this.isContentForTagData(tagName) ? new DataNode(data) : new TextNode(data));
        el.appendChild(node);
        this.onNodeInserted(node, characterToken);
    }

    private void insertNode(Node node, @Nullable Token token) {
        if (this.stack.isEmpty()) {
            this.doc.appendChild(node);
        } else if (this.isFosterInserts() && StringUtil.inSorted(this.currentElement().normalName(), HtmlTreeBuilderState.Constants.InTableFoster)) {
            this.insertInFosterParent(node);
        } else {
            this.currentElement().appendChild(node);
        }
        if (node instanceof Element && ((Element)node).tag().isFormListed() && this.formElement != null) {
            this.formElement.addElement((Element)node);
        }
        this.onNodeInserted(node, token);
    }

    Element pop() {
        int size = this.stack.size();
        return (Element)this.stack.remove(size - 1);
    }

    void push(Element element) {
        this.stack.add(element);
    }

    ArrayList<Element> getStack() {
        return this.stack;
    }

    boolean onStack(Element el) {
        return HtmlTreeBuilder.onStack(this.stack, el);
    }

    boolean onStack(String elName) {
        return this.getFromStack(elName) != null;
    }

    private static boolean onStack(ArrayList<Element> queue, Element element) {
        int bottom = queue.size() - 1;
        int upper = bottom >= 256 ? bottom - 256 : 0;
        for (int pos = bottom; pos >= upper; --pos) {
            Element next = queue.get(pos);
            if (next != element) continue;
            return true;
        }
        return false;
    }

    @Nullable
    Element getFromStack(String elName) {
        int bottom = this.stack.size() - 1;
        int upper = bottom >= 256 ? bottom - 256 : 0;
        for (int pos = bottom; pos >= upper; --pos) {
            Element next = (Element)this.stack.get(pos);
            if (!next.normalName().equals(elName)) continue;
            return next;
        }
        return null;
    }

    boolean removeFromStack(Element el) {
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            Element next = (Element)this.stack.get(pos);
            if (next != el) continue;
            this.stack.remove(pos);
            return true;
        }
        return false;
    }

    @Nullable
    Element popStackToClose(String elName) {
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            Element el = (Element)this.stack.get(pos);
            this.stack.remove(pos);
            if (!el.normalName().equals(elName)) continue;
            if (this.currentToken instanceof Token.EndTag) {
                this.onNodeClosed(el, this.currentToken);
            }
            return el;
        }
        return null;
    }

    void popStackToClose(String ... elNames) {
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            Element next = (Element)this.stack.get(pos);
            this.stack.remove(pos);
            if (StringUtil.inSorted(next.normalName(), elNames)) break;
        }
    }

    void popStackToBefore(String elName) {
        Element next;
        for (int pos = this.stack.size() - 1; pos >= 0 && !(next = (Element)this.stack.get(pos)).normalName().equals(elName); --pos) {
            this.stack.remove(pos);
        }
    }

    void clearStackToTableContext() {
        this.clearStackToContext("table", "template");
    }

    void clearStackToTableBodyContext() {
        this.clearStackToContext("tbody", "tfoot", "thead", "template");
    }

    void clearStackToTableRowContext() {
        this.clearStackToContext("tr", "template");
    }

    private void clearStackToContext(String ... nodeNames) {
        Element next;
        for (int pos = this.stack.size() - 1; pos >= 0 && !StringUtil.in((next = (Element)this.stack.get(pos)).normalName(), nodeNames) && !next.normalName().equals("html"); --pos) {
            this.stack.remove(pos);
        }
    }

    @Nullable
    Element aboveOnStack(Element el) {
        assert (this.onStack(el));
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            Element next = (Element)this.stack.get(pos);
            if (next != el) continue;
            return (Element)this.stack.get(pos - 1);
        }
        return null;
    }

    void insertOnStackAfter(Element after, Element in) {
        int i = this.stack.lastIndexOf(after);
        Validate.isTrue(i != -1);
        this.stack.add(i + 1, in);
    }

    void replaceOnStack(Element out, Element in) {
        this.replaceInQueue(this.stack, out, in);
    }

    private void replaceInQueue(ArrayList<Element> queue, Element out, Element in) {
        int i = queue.lastIndexOf(out);
        Validate.isTrue(i != -1);
        queue.set(i, in);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean resetInsertionMode() {
        boolean last = false;
        int bottom = this.stack.size() - 1;
        int upper = bottom >= 256 ? bottom - 256 : 0;
        HtmlTreeBuilderState origState = this.state;
        if (this.stack.size() == 0) {
            this.transition(HtmlTreeBuilderState.InBody);
        }
        block31: for (int pos = bottom; pos >= upper; --pos) {
            String name;
            Element node = (Element)this.stack.get(pos);
            if (pos == upper) {
                last = true;
                if (this.fragmentParsing) {
                    node = this.contextElement;
                }
            }
            switch (name = node != null ? node.normalName() : "") {
                case "select": {
                    this.transition(HtmlTreeBuilderState.InSelect);
                    break block31;
                }
                case "td": 
                case "th": {
                    if (last) break;
                    this.transition(HtmlTreeBuilderState.InCell);
                    break block31;
                }
                case "tr": {
                    this.transition(HtmlTreeBuilderState.InRow);
                    break block31;
                }
                case "tbody": 
                case "thead": 
                case "tfoot": {
                    this.transition(HtmlTreeBuilderState.InTableBody);
                    break block31;
                }
                case "caption": {
                    this.transition(HtmlTreeBuilderState.InCaption);
                    break block31;
                }
                case "colgroup": {
                    this.transition(HtmlTreeBuilderState.InColumnGroup);
                    break block31;
                }
                case "table": {
                    this.transition(HtmlTreeBuilderState.InTable);
                    break block31;
                }
                case "template": {
                    HtmlTreeBuilderState tmplState = this.currentTemplateMode();
                    Validate.notNull((Object)tmplState, "Bug: no template insertion mode on stack!");
                    this.transition(tmplState);
                    break block31;
                }
                case "head": {
                    if (last) break;
                    this.transition(HtmlTreeBuilderState.InHead);
                    break block31;
                }
                case "body": {
                    this.transition(HtmlTreeBuilderState.InBody);
                    break block31;
                }
                case "frameset": {
                    this.transition(HtmlTreeBuilderState.InFrameset);
                    break block31;
                }
                case "html": {
                    this.transition(this.headElement == null ? HtmlTreeBuilderState.BeforeHead : HtmlTreeBuilderState.AfterHead);
                    break block31;
                }
            }
            if (!last) continue;
            this.transition(HtmlTreeBuilderState.InBody);
            break;
        }
        if (this.state == origState) return false;
        return true;
    }

    void resetBody() {
        if (!this.onStack("body")) {
            this.stack.add(this.doc.body());
        }
        this.transition(HtmlTreeBuilderState.InBody);
    }

    private boolean inSpecificScope(String targetName, String[] baseTypes, String[] extraTypes) {
        this.specificScopeTarget[0] = targetName;
        return this.inSpecificScope(this.specificScopeTarget, baseTypes, extraTypes);
    }

    private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
        int bottom = this.stack.size() - 1;
        int top = bottom > 100 ? bottom - 100 : 0;
        for (int pos = bottom; pos >= top; --pos) {
            String elName = ((Element)this.stack.get(pos)).normalName();
            if (StringUtil.inSorted(elName, targetNames)) {
                return true;
            }
            if (StringUtil.inSorted(elName, baseTypes)) {
                return false;
            }
            if (extraTypes == null || !StringUtil.inSorted(elName, extraTypes)) continue;
            return false;
        }
        return false;
    }

    boolean inScope(String[] targetNames) {
        return this.inSpecificScope(targetNames, TagsSearchInScope, null);
    }

    boolean inScope(String targetName) {
        return this.inScope(targetName, null);
    }

    boolean inScope(String targetName, String[] extras) {
        return this.inSpecificScope(targetName, TagsSearchInScope, extras);
    }

    boolean inListItemScope(String targetName) {
        return this.inScope(targetName, TagSearchList);
    }

    boolean inButtonScope(String targetName) {
        return this.inScope(targetName, TagSearchButton);
    }

    boolean inTableScope(String targetName) {
        return this.inSpecificScope(targetName, TagSearchTableScope, null);
    }

    boolean inSelectScope(String targetName) {
        for (int pos = this.stack.size() - 1; pos >= 0; --pos) {
            Element el = (Element)this.stack.get(pos);
            String elName = el.normalName();
            if (elName.equals(targetName)) {
                return true;
            }
            if (StringUtil.inSorted(elName, TagSearchSelectScope)) continue;
            return false;
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    void setHeadElement(Element headElement) {
        this.headElement = headElement;
    }

    Element getHeadElement() {
        return this.headElement;
    }

    boolean isFosterInserts() {
        return this.fosterInserts;
    }

    void setFosterInserts(boolean fosterInserts) {
        this.fosterInserts = fosterInserts;
    }

    @Nullable
    FormElement getFormElement() {
        return this.formElement;
    }

    void setFormElement(FormElement formElement) {
        this.formElement = formElement;
    }

    void newPendingTableCharacters() {
        this.pendingTableCharacters = new ArrayList<String>();
    }

    List<String> getPendingTableCharacters() {
        return this.pendingTableCharacters;
    }

    void generateImpliedEndTags(String excludeTag) {
        while (StringUtil.inSorted(this.currentElement().normalName(), TagSearchEndTags) && (excludeTag == null || !this.currentElementIs(excludeTag))) {
            this.pop();
        }
    }

    void generateImpliedEndTags() {
        this.generateImpliedEndTags(false);
    }

    void generateImpliedEndTags(boolean thorough) {
        String[] search;
        String[] stringArray = search = thorough ? TagThoroughSearchEndTags : TagSearchEndTags;
        while (StringUtil.inSorted(this.currentElement().normalName(), search)) {
            this.pop();
        }
    }

    void closeElement(String name) {
        this.generateImpliedEndTags(name);
        if (!name.equals(this.currentElement().normalName())) {
            this.error(this.state());
        }
        this.popStackToClose(name);
    }

    boolean isSpecial(Element el) {
        String name = el.normalName();
        return StringUtil.inSorted(name, TagSearchSpecial);
    }

    Element lastFormattingElement() {
        return this.formattingElements.size() > 0 ? this.formattingElements.get(this.formattingElements.size() - 1) : null;
    }

    int positionOfElement(Element el) {
        for (int i = 0; i < this.formattingElements.size(); ++i) {
            if (el != this.formattingElements.get(i)) continue;
            return i;
        }
        return -1;
    }

    Element removeLastFormattingElement() {
        int size = this.formattingElements.size();
        if (size > 0) {
            return this.formattingElements.remove(size - 1);
        }
        return null;
    }

    void pushActiveFormattingElements(Element in) {
        this.checkActiveFormattingElements(in);
        this.formattingElements.add(in);
    }

    void pushWithBookmark(Element in, int bookmark) {
        this.checkActiveFormattingElements(in);
        try {
            this.formattingElements.add(bookmark, in);
        }
        catch (IndexOutOfBoundsException e) {
            this.formattingElements.add(in);
        }
    }

    void checkActiveFormattingElements(Element in) {
        Element el;
        int numSeen = 0;
        int size = this.formattingElements.size() - 1;
        int ceil = size - 12;
        if (ceil < 0) {
            ceil = 0;
        }
        for (int pos = size; pos >= ceil && (el = this.formattingElements.get(pos)) != null; --pos) {
            if (this.isSameFormattingElement(in, el)) {
                ++numSeen;
            }
            if (numSeen != 3) continue;
            this.formattingElements.remove(pos);
            break;
        }
    }

    private boolean isSameFormattingElement(Element a, Element b) {
        return a.normalName().equals(b.normalName()) && a.attributes().equals(b.attributes());
    }

    void reconstructFormattingElements() {
        if (this.stack.size() > 256) {
            return;
        }
        Element last = this.lastFormattingElement();
        if (last == null || this.onStack(last)) {
            return;
        }
        Element entry = last;
        int size = this.formattingElements.size();
        int ceil = size - 12;
        if (ceil < 0) {
            ceil = 0;
        }
        int pos = size - 1;
        boolean skip = false;
        do {
            if (pos != ceil) continue;
            skip = true;
            break;
        } while ((entry = this.formattingElements.get(--pos)) != null && !this.onStack(entry));
        do {
            if (!skip) {
                entry = this.formattingElements.get(++pos);
            }
            Validate.notNull(entry);
            skip = false;
            Element newEl = new Element(this.tagFor(entry.normalName(), this.settings), null, entry.attributes().clone());
            this.insert(newEl);
            this.formattingElements.set(pos, newEl);
        } while (pos != size - 1);
    }

    void clearFormattingElementsToLastMarker() {
        Element el;
        while (!this.formattingElements.isEmpty() && (el = this.removeLastFormattingElement()) != null) {
        }
    }

    void removeFromActiveFormattingElements(Element el) {
        for (int pos = this.formattingElements.size() - 1; pos >= 0; --pos) {
            Element next = this.formattingElements.get(pos);
            if (next != el) continue;
            this.formattingElements.remove(pos);
            break;
        }
    }

    boolean isInActiveFormattingElements(Element el) {
        return HtmlTreeBuilder.onStack(this.formattingElements, el);
    }

    Element getActiveFormattingElement(String nodeName) {
        Element next;
        for (int pos = this.formattingElements.size() - 1; pos >= 0 && (next = this.formattingElements.get(pos)) != null; --pos) {
            if (!next.normalName().equals(nodeName)) continue;
            return next;
        }
        return null;
    }

    void replaceActiveFormattingElement(Element out, Element in) {
        this.replaceInQueue(this.formattingElements, out, in);
    }

    void insertMarkerToFormattingElements() {
        this.formattingElements.add(null);
    }

    void insertInFosterParent(Node in) {
        Element fosterParent;
        Element lastTable = this.getFromStack("table");
        boolean isLastTableParent = false;
        if (lastTable != null) {
            if (lastTable.parent() != null) {
                fosterParent = lastTable.parent();
                isLastTableParent = true;
            } else {
                fosterParent = this.aboveOnStack(lastTable);
            }
        } else {
            fosterParent = (Element)this.stack.get(0);
        }
        if (isLastTableParent) {
            Validate.notNull(lastTable);
            lastTable.before(in);
        } else {
            fosterParent.appendChild(in);
        }
    }

    void pushTemplateMode(HtmlTreeBuilderState state) {
        this.tmplInsertMode.add(state);
    }

    @Nullable
    HtmlTreeBuilderState popTemplateMode() {
        if (this.tmplInsertMode.size() > 0) {
            return this.tmplInsertMode.remove(this.tmplInsertMode.size() - 1);
        }
        return null;
    }

    int templateModeSize() {
        return this.tmplInsertMode.size();
    }

    @Nullable
    HtmlTreeBuilderState currentTemplateMode() {
        return this.tmplInsertMode.size() > 0 ? this.tmplInsertMode.get(this.tmplInsertMode.size() - 1) : null;
    }

    public String toString() {
        return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + (Object)((Object)this.state) + ", currentElement=" + this.currentElement() + '}';
    }

    @Override
    protected boolean isContentForTagData(String normalName) {
        return normalName.equals("script") || normalName.equals("style");
    }
}
