package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$47
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.current();
        switch (c) {
            case '-': {
                t.advanceTransition(CommentEndDash);
                break;
            }
            case '\u0000': {
                t.error(this);
                r.advance();
                t.commentPending.append('�');
                break;
            }
            case '￿': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(Data);
                break;
            }
            default: {
                t.commentPending.append(r.consumeToAny('-', '\u0000'));
            }
        }
    }
}
