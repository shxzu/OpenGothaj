package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$52
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matchesAsciiAlpha()) {
            t.createDoctypePending();
            t.transition(DoctypeName);
            return;
        }
        char c = r.consume();
        switch (c) {
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                break;
            }
            case '\u0000': {
                t.error(this);
                t.createDoctypePending();
                t.doctypePending.name.append('�');
                t.transition(DoctypeName);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.createDoctypePending();
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(Data);
                break;
            }
            default: {
                t.createDoctypePending();
                t.doctypePending.name.append(c);
                t.transition(DoctypeName);
            }
        }
    }
}
