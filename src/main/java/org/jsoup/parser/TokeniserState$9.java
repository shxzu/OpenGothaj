package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$9
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.emit("</");
            t.transition(Data);
        } else if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.transition(TagName);
        } else if (r.matches('>')) {
            t.error(this);
            t.advanceTransition(Data);
        } else {
            t.error(this);
            t.createBogusCommentPending();
            t.commentPending.append('/');
            t.transition(BogusComment);
        }
    }
}
