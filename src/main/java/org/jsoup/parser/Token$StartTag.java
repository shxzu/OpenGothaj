package org.jsoup.parser;

import org.jsoup.nodes.Attributes;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Token;

final class Token$StartTag
extends Token.Tag {
    Token$StartTag() {
        this.type = Token.TokenType.StartTag;
    }

    @Override
    Token.Tag reset() {
        super.reset();
        this.attributes = null;
        return this;
    }

    Token$StartTag nameAttr(String name, Attributes attributes) {
        this.tagName = name;
        this.attributes = attributes;
        this.normalName = ParseSettings.normalName(this.tagName);
        return this;
    }

    @Override
    public String toString() {
        if (this.hasAttributes() && this.attributes.size() > 0) {
            return "<" + this.toStringName() + " " + this.attributes.toString() + ">";
        }
        return "<" + this.toStringName() + ">";
    }
}
