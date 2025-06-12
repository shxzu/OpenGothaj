package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$63
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '\"': {
                t.transition(AfterDoctypeSystemIdentifier);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.doctypePending.systemIdentifier.append('�');
                break;
            }
            case '>': {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(Data);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(Data);
                break;
            }
            default: {
                t.doctypePending.systemIdentifier.append(c);
            }
        }
    }
}
