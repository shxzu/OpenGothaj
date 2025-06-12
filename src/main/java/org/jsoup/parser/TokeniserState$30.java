package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$30
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '-': {
                t.emit(c);
                t.transition(ScriptDataDoubleEscapedDashDash);
                break;
            }
            case '<': {
                t.emit(c);
                t.transition(ScriptDataDoubleEscapedLessthanSign);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.emit('�');
                t.transition(ScriptDataDoubleEscaped);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            default: {
                t.emit(c);
                t.transition(ScriptDataDoubleEscaped);
            }
        }
    }
}
