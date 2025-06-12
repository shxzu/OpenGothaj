package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$41
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                t.transition(BeforeAttributeName);
                break;
            }
            case '/': {
                t.transition(SelfClosingStartTag);
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(Data);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            default: {
                r.unconsume();
                t.error(this);
                t.transition(BeforeAttributeName);
            }
        }
    }
}
