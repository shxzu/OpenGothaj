package org.jsoup.parser;

import org.jsoup.parser.Token;

final class Token$EOF
extends Token {
    Token$EOF() {
        super(null);
        this.type = Token.TokenType.EOF;
    }

    @Override
    Token reset() {
        super.reset();
        return this;
    }

    public String toString() {
        return "";
    }
}
