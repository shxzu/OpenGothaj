package org.jsoup.parser;

import org.jsoup.parser.Token;

final class Token$Comment
extends Token {
    private final StringBuilder data = new StringBuilder();
    private String dataS;
    boolean bogus = false;

    @Override
    Token reset() {
        super.reset();
        Token$Comment.reset(this.data);
        this.dataS = null;
        this.bogus = false;
        return this;
    }

    Token$Comment() {
        super(null);
        this.type = Token.TokenType.Comment;
    }

    String getData() {
        return this.dataS != null ? this.dataS : this.data.toString();
    }

    final Token$Comment append(String append) {
        this.ensureData();
        if (this.data.length() == 0) {
            this.dataS = append;
        } else {
            this.data.append(append);
        }
        return this;
    }

    final Token$Comment append(char append) {
        this.ensureData();
        this.data.append(append);
        return this;
    }

    private void ensureData() {
        if (this.dataS != null) {
            this.data.append(this.dataS);
            this.dataS = null;
        }
    }

    public String toString() {
        return "<!--" + this.getData() + "-->";
    }
}
