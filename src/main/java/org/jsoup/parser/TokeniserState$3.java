package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$3
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        switch (r.current()) {
            case '&': {
                t.advanceTransition(CharacterReferenceInRcdata);
                break;
            }
            case '<': {
                t.advanceTransition(RcdataLessthanSign);
                break;
            }
            case '\u0000': {
                t.error(this);
                r.advance();
                t.emit('�');
                break;
            }
            case '￿': {
                t.emit(new Token.EOF());
                break;
            }
            default: {
                String data = r.consumeData();
                t.emit(data);
            }
        }
    }
}
