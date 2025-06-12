package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$59
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        char c = r.consume();
        switch (c) {
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                t.transition(BetweenDoctypePublicAndSystemIdentifiers);
                break;
            }
            case '>': {
                t.emitDoctypePending();
                t.transition(Data);
                break;
            }
            case '\"': {
                t.error(this);
                t.transition(DoctypeSystemIdentifier_doubleQuoted);
                break;
            }
            case '\'': {
                t.error(this);
                t.transition(DoctypeSystemIdentifier_singleQuoted);
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
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.transition(BogusDoctype);
            }
        }
    }
}
