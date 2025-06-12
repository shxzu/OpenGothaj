package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$66
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '>': {
                t.emitDoctypePending();
                t.transition(Data);
                break;
            }
            case '￿': {
                t.emitDoctypePending();
                t.transition(Data);
                break;
            }
        }
    }
}
