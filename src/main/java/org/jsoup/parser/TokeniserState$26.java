package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$26
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.tagPending.appendTagName(r.current());
            t.dataBuffer.append(r.current());
            t.advanceTransition(ScriptDataEscapedEndTagName);
        } else {
            t.emit("</");
            t.transition(ScriptDataEscaped);
        }
    }
}
