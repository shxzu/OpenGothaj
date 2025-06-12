package org.jsoup.parser;

import org.jsoup.parser.Token;

class Token$Character
extends Token {
    private String data;

    Token$Character() {
        super(null);
        this.type = Token.TokenType.Character;
    }

    @Override
    Token reset() {
        super.reset();
        this.data = null;
        return this;
    }

    Token$Character data(String data) {
        this.data = data;
        return this;
    }

    String getData() {
        return this.data;
    }

    public String toString() {
        return this.getData();
    }
}
