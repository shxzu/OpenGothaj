package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$54
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.doctypePending.forceQuirks = true;
            t.emitDoctypePending();
            t.transition(Data);
            return;
        }
        if (r.matchesAny('\t', '\n', '\r', '\f', ' ')) {
            r.advance();
        } else if (r.matches('>')) {
            t.emitDoctypePending();
            t.advanceTransition(Data);
        } else if (r.matchConsumeIgnoreCase("PUBLIC")) {
            t.doctypePending.pubSysKey = "PUBLIC";
            t.transition(AfterDoctypePublicKeyword);
        } else if (r.matchConsumeIgnoreCase("SYSTEM")) {
            t.doctypePending.pubSysKey = "SYSTEM";
            t.transition(AfterDoctypeSystemKeyword);
        } else {
            t.error(this);
            t.doctypePending.forceQuirks = true;
            t.advanceTransition(BogusDoctype);
        }
    }
}
