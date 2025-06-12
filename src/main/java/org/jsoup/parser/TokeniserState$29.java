package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$29
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.current();
        switch (c) {
            case '-': {
                t.emit(c);
                t.advanceTransition(ScriptDataDoubleEscapedDash);
                break;
            }
            case '<': {
                t.emit(c);
                t.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
                break;
            }
            case '\u0000': {
                t.error(this);
                r.advance();
                t.emit('�');
                break;
            }
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            default: {
                String data = r.consumeToAny('-', '<', '\u0000');
                t.emit(data);
            }
        }
    }
}
