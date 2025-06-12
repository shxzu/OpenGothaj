package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$36
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
                break;
            }
            case '/': {
                t.transition(SelfClosingStartTag);
                break;
            }
            case '=': {
                t.transition(BeforeAttributeValue);
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(Data);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.tagPending.appendAttributeName('�');
                t.transition(AttributeName);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            case '\"': 
            case '\'': 
            case '<': {
                t.error(this);
                t.tagPending.newAttribute();
                t.tagPending.appendAttributeName(c);
                t.transition(AttributeName);
                break;
            }
            default: {
                t.tagPending.newAttribute();
                r.unconsume();
                t.transition(AttributeName);
            }
        }
    }
}
