package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$67
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        String data = r.consumeTo("]]>");
        t.dataBuffer.append(data);
        if (r.matchConsume("]]>") || r.isEmpty()) {
            t.emit(new Token.CData(t.dataBuffer.toString()));
            t.transition(Data);
        }
    }
}
