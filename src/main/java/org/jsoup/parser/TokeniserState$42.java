package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$42
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '>': {
                t.tagPending.selfClosing = true;
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
