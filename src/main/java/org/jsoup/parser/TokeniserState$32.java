package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$32
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matches('/')) {
            t.emit('/');
            t.createTempBuffer();
            t.advanceTransition(ScriptDataDoubleEscapeEnd);
        } else {
            t.transition(ScriptDataDoubleEscaped);
        }
    }
}
