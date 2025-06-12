package org.jsoup.parser;

import javax.annotation.Nullable;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Token;

abstract class Token$Tag
extends Token {
    @Nullable
    protected String tagName;
    @Nullable
    protected String normalName;
    private final StringBuilder attrName = new StringBuilder();
    @Nullable
    private String attrNameS;
    private boolean hasAttrName = false;
    private final StringBuilder attrValue = new StringBuilder();
    @Nullable
    private String attrValueS;
    private boolean hasAttrValue = false;
    private boolean hasEmptyAttrValue = false;
    boolean selfClosing = false;
    @Nullable
    Attributes attributes;
    private static final int MaxAttributes = 512;

    Token$Tag() {
        super(null);
    }

    @Override
    Token$Tag reset() {
        super.reset();
        this.tagName = null;
        this.normalName = null;
        Token$Tag.reset(this.attrName);
        this.attrNameS = null;
        this.hasAttrName = false;
        Token$Tag.reset(this.attrValue);
        this.attrValueS = null;
        this.hasEmptyAttrValue = false;
        this.hasAttrValue = false;
        this.selfClosing = false;
        this.attributes = null;
        return this;
    }

    final void newAttribute() {
        if (this.attributes == null) {
            this.attributes = new Attributes();
        }
        if (this.hasAttrName && this.attributes.size() < 512) {
            String name = this.attrName.length() > 0 ? this.attrName.toString() : this.attrNameS;
            if ((name = name.trim()).length() > 0) {
                String value = this.hasAttrValue ? (this.attrValue.length() > 0 ? this.attrValue.toString() : this.attrValueS) : (this.hasEmptyAttrValue ? "" : null);
                this.attributes.add(name, value);
            }
        }
        Token$Tag.reset(this.attrName);
        this.attrNameS = null;
        this.hasAttrName = false;
        Token$Tag.reset(this.attrValue);
        this.attrValueS = null;
        this.hasAttrValue = false;
        this.hasEmptyAttrValue = false;
    }

    final boolean hasAttributes() {
        return this.attributes != null;
    }

    final boolean hasAttribute(String key) {
        return this.attributes != null && this.attributes.hasKey(key);
    }

    final void finaliseTag() {
        if (this.hasAttrName) {
            this.newAttribute();
        }
    }

    final String name() {
        Validate.isFalse(this.tagName == null || this.tagName.length() == 0);
        return this.tagName;
    }

    final String normalName() {
        return this.normalName;
    }

    final String toStringName() {
        return this.tagName != null ? this.tagName : "[unset]";
    }

    final Token$Tag name(String name) {
        this.tagName = name;
        this.normalName = ParseSettings.normalName(this.tagName);
        return this;
    }

    final boolean isSelfClosing() {
        return this.selfClosing;
    }

    final void appendTagName(String append) {
        append = append.replace('\u0000', '�');
        this.tagName = this.tagName == null ? append : this.tagName.concat(append);
        this.normalName = ParseSettings.normalName(this.tagName);
    }

    final void appendTagName(char append) {
        this.appendTagName(String.valueOf(append));
    }

    final void appendAttributeName(String append) {
        append = append.replace('\u0000', '�');
        this.ensureAttrName();
        if (this.attrName.length() == 0) {
            this.attrNameS = append;
        } else {
            this.attrName.append(append);
        }
    }

    final void appendAttributeName(char append) {
        this.ensureAttrName();
        this.attrName.append(append);
    }

    final void appendAttributeValue(String append) {
        this.ensureAttrValue();
        if (this.attrValue.length() == 0) {
            this.attrValueS = append;
        } else {
            this.attrValue.append(append);
        }
    }

    final void appendAttributeValue(char append) {
        this.ensureAttrValue();
        this.attrValue.append(append);
    }

    final void appendAttributeValue(char[] append) {
        this.ensureAttrValue();
        this.attrValue.append(append);
    }

    final void appendAttributeValue(int[] appendCodepoints) {
        this.ensureAttrValue();
        for (int codepoint : appendCodepoints) {
            this.attrValue.appendCodePoint(codepoint);
        }
    }

    final void setEmptyAttributeValue() {
        this.hasEmptyAttrValue = true;
    }

    private void ensureAttrName() {
        this.hasAttrName = true;
        if (this.attrNameS != null) {
            this.attrName.append(this.attrNameS);
            this.attrNameS = null;
        }
    }

    private void ensureAttrValue() {
        this.hasAttrValue = true;
        if (this.attrValueS != null) {
            this.attrValue.append(this.attrValueS);
            this.attrValueS = null;
        }
    }

    public abstract String toString();
}
