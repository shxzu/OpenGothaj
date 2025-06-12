package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$1
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        switch (r.current()) {
            case '&': {
                t.advanceTransition(CharacterReferenceInData);
                break;
            }
            case '<': {
                t.advanceTransition(TagOpen);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.emit(r.consume());
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
