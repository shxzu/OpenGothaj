package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$21
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition(ScriptDataEscapedDashDash);
        } else {
            t.transition(ScriptData);
        }
    }
}
