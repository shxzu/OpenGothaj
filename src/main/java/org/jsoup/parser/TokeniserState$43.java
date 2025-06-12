package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$43
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        t.commentPending.append(r.consumeTo('>'));
        char next = r.current();
        if (next == '>' || next == '￿') {
            r.consume();
            t.emitCommentPending();
            t.transition(Data);
        }
    }
}
