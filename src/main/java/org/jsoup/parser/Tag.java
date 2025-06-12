package org.jsoup.parser;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.parser.ParseSettings;

public class Tag
implements Cloneable {
    private static final Map<String, Tag> tags;
    private String tagName;
    private String normalName;
    private boolean isBlock = true;
    private boolean formatAsBlock = true;
    private boolean empty = false;
    private boolean selfClosing = false;
    private boolean preserveWhitespace = false;
    private boolean formList = false;
    private boolean formSubmit = false;
    private static final String[] blockTags;
    private static final String[] inlineTags;
    private static final String[] emptyTags;
    private static final String[] formatAsInlineTags;
    private static final String[] preserveWhitespaceTags;
    private static final String[] formListedTags;
    private static final String[] formSubmitTags;

    private Tag(String tagName) {
        this.tagName = tagName;
        this.normalName = Normalizer.lowerCase(tagName);
    }

    public String getName() {
        return this.tagName;
    }

    public String normalName() {
        return this.normalName;
    }

    public static Tag valueOf(String tagName, ParseSettings settings) {
        Validate.notNull(tagName);
        Tag tag = tags.get(tagName);
        if (tag == null) {
            tagName = settings.normalizeTag(tagName);
            Validate.notEmpty(tagName);
            String normalName = Normalizer.lowerCase(tagName);
            tag = tags.get(normalName);
            if (tag == null) {
                tag = new Tag(tagName);
                tag.isBlock = false;
            } else if (settings.preserveTagCase() && !tagName.equals(normalName)) {
                tag = tag.clone();
                tag.tagName = tagName;
            }
        }
        return tag;
    }

    public static Tag valueOf(String tagName) {
        return Tag.valueOf(tagName, ParseSettings.preserveCase);
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }

    public boolean isInline() {
        return !this.isBlock;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isSelfClosing() {
        return this.empty || this.selfClosing;
    }

    public boolean isKnownTag() {
        return tags.containsKey(this.tagName);
    }

    public static boolean isKnownTag(String tagName) {
        return tags.containsKey(tagName);
    }

    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }

    public boolean isFormListed() {
        return this.formList;
    }

    public boolean isFormSubmittable() {
        return this.formSubmit;
    }

    Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag)o;
        if (!this.tagName.equals(tag.tagName)) {
            return false;
        }
        if (this.empty != tag.empty) {
            return false;
        }
        if (this.formatAsBlock != tag.formatAsBlock) {
            return false;
        }
        if (this.isBlock != tag.isBlock) {
            return false;
        }
        if (this.preserveWhitespace != tag.preserveWhitespace) {
            return false;
        }
        if (this.selfClosing != tag.selfClosing) {
            return false;
        }
        if (this.formList != tag.formList) {
            return false;
        }
        return this.formSubmit == tag.formSubmit;
    }

    public int hashCode() {
        int result = this.tagName.hashCode();
        result = 31 * result + (this.isBlock ? 1 : 0);
        result = 31 * result + (this.formatAsBlock ? 1 : 0);
        result = 31 * result + (this.empty ? 1 : 0);
        result = 31 * result + (this.selfClosing ? 1 : 0);
        result = 31 * result + (this.preserveWhitespace ? 1 : 0);
        result = 31 * result + (this.formList ? 1 : 0);
        result = 31 * result + (this.formSubmit ? 1 : 0);
        return result;
    }

    public String toString() {
        return this.tagName;
    }

    protected Tag clone() {
        try {
            return (Tag)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void register(Tag tag) {
        tags.put(tag.tagName, tag);
    }

    static {
        Tag tag;
        tags = new HashMap<String, Tag>();
        blockTags = new String[]{"html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame", "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", "video", "audio", "canvas", "details", "menu", "plaintext", "template", "article", "main", "svg", "math", "center", "template", "dir", "applet", "marquee", "listing"};
        inlineTags = new String[]{"object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup", "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track", "summary", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track", "data", "bdi", "s", "strike", "nobr"};
        emptyTags = new String[]{"meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track"};
        formatAsInlineTags = new String[]{"title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style", "ins", "del", "s"};
        preserveWhitespaceTags = new String[]{"pre", "plaintext", "title", "textarea"};
        formListedTags = new String[]{"button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"};
        formSubmitTags = new String[]{"input", "keygen", "object", "select", "textarea"};
        for (String tagName : blockTags) {
            tag = new Tag(tagName);
            Tag.register(tag);
        }
        for (String tagName : inlineTags) {
            tag = new Tag(tagName);
            tag.isBlock = false;
            tag.formatAsBlock = false;
            Tag.register(tag);
        }
        for (String tagName : emptyTags) {
            tag = tags.get(tagName);
            Validate.notNull(tag);
            tag.empty = true;
        }
        for (String tagName : formatAsInlineTags) {
            tag = tags.get(tagName);
            Validate.notNull(tag);
            tag.formatAsBlock = false;
        }
        for (String tagName : preserveWhitespaceTags) {
            tag = tags.get(tagName);
            Validate.notNull(tag);
            tag.preserveWhitespace = true;
        }
        for (String tagName : formListedTags) {
            tag = tags.get(tagName);
            Validate.notNull(tag);
            tag.formList = true;
        }
        for (String tagName : formSubmitTags) {
            tag = tags.get(tagName);
            Validate.notNull(tag);
            tag.formSubmit = true;
        }
    }
}
