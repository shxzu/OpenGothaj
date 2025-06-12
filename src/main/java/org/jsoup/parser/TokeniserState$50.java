package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$50
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '-': {
                t.commentPending.append("--!");
                t.transition(CommentEndDash);
                break;
            }
            case '>': {
                t.emitCommentPending();
                t.transition(Data);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.commentPending.append("--!").append('�');
                t.transition(Comment);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.emitCommentPending();
                t.transition(Data);
                break;
            }
            default: {
                t.commentPending.append("--!").append(c);
                t.transition(Comment);
            }
        }
    }
}
