package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$44
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matchConsume("--")) {
            t.createCommentPending();
            t.transition(CommentStart);
        } else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
            t.transition(Doctype);
        } else if (r.matchConsume("[CDATA[")) {
            t.createTempBuffer();
            t.transition(CdataSection);
        } else {
            t.error(this);
            t.createBogusCommentPending();
            t.transition(BogusComment);
        }
    }
}
