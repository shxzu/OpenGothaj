package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$4
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        TokeniserState.readCharRef(t, TokeniserState$4.Rcdata);
    }
}
