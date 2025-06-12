package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$33
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        TokeniserState.handleDataDoubleEscapeTag(t, r, TokeniserState$33.ScriptDataEscaped, TokeniserState$33.ScriptDataDoubleEscaped);
    }
}
