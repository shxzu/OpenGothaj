package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$11
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(RCDATAEndTagOpen);
        } else if (r.matchesAsciiAlpha() && t.appropriateEndTagName() != null && !r.containsIgnoreCase(t.appropriateEndTagSeq())) {
            t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
            t.emitTagPending();
            t.transition(TagOpen);
        } else {
            t.emit("<");
            t.transition(Rcdata);
        }
    }
}
