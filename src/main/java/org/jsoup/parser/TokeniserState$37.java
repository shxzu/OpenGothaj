package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$37
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
            case '\"': {
                t.transition(AttributeValue_doubleQuoted);
                break;
            }
            case '&': {
                r.unconsume();
                t.transition(AttributeValue_unquoted);
                break;
            }
            case '\'': {
                t.transition(AttributeValue_singleQuoted);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.tagPending.appendAttributeValue('�');
                t.transition(AttributeValue_unquoted);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.emitTagPending();
                t.transition(Data);
                break;
            }
            case '>': {
                t.error(this);
                t.emitTagPending();
                t.transition(Data);
                break;
            }
            case '<': 
            case '=': 
            case '`': {
                t.error(this);
                t.tagPending.appendAttributeValue(c);
                t.transition(AttributeValue_unquoted);
                break;
            }
            default: {
                r.unconsume();
                t.transition(AttributeValue_unquoted);
            }
        }
    }
}
