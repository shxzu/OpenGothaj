package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$15
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        TokeniserState.readEndTag(t, r, TokeniserState$15.RawtextEndTagName, TokeniserState$15.Rawtext);
    }
}
