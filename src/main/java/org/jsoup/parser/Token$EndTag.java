package org.jsoup.parser;

import org.jsoup.parser.Token;

final class Token$EndTag
extends Token.Tag {
    Token$EndTag() {
        this.type = Token.TokenType.EndTag;
    }

    @Override
    public String toString() {
        return "</" + this.toStringName() + ">";
    }
}
