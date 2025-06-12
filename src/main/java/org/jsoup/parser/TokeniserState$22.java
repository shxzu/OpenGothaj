package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$22
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition(Data);
            return;
        }
        switch (r.current()) {
            case '-': {
                t.emit('-');
                t.advanceTransition(ScriptDataEscapedDash);
                break;
            }
            case '<': {
                t.advanceTransition(ScriptDataEscapedLessthanSign);
                break;
            }
            case '\u0000': {
                t.error(this);
                r.advance();
                t.emit('�');
                break;
            }
            default: {
                String data = r.consumeToAny('-', '<', '\u0000');
                t.emit(data);
            }
        }
    }
}
