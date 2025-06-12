package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$14
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(RawtextEndTagOpen);
        } else {
            t.emit('<');
            t.transition(Rawtext);
        }
    }
}
