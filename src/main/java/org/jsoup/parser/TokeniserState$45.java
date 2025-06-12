package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$45
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '-': {
                t.transition(CommentStartDash);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.commentPending.append('�');
                t.transition(Comment);
                break;
            }
            case '>': {
                t.error(this);
                t.emitCommentPending();
                t.transition(Data);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(Data);
                break;
            }
            default: {
                r.unconsume();
                t.transition(Comment);
            }
        }
    }
}
