package org.jsoup.parser;

import org.jsoup.parser.Token;

final class Token$CData
extends Token.Character {
    Token$CData(String data) {
        this.data(data);
    }

    @Override
    public String toString() {
        return "<![CDATA[" + this.getData() + "]]>";
    }
}
