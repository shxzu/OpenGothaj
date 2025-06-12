package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$23
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition(Data);
            return;
        }
        char c = r.consume();
        switch (c) {
            case '-': {
                t.emit(c);
                t.transition(ScriptDataEscapedDashDash);
                break;
            }
            case '<': {
                t.transition(ScriptDataEscapedLessthanSign);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.emit('�');
                t.transition(ScriptDataEscaped);
                break;
            }
            default: {
                t.emit(c);
                t.transition(ScriptDataEscaped);
            }
        }
    }
}
