package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$40
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        String value = r.consumeToAnySorted(attributeValueUnquoted);
        if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
        }
        char c = r.consume();
        switch (c) {
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                t.transition(BeforeAttributeName);
                break;
            }
            case '&': {
                int[] ref = t.consumeCharacterReference(Character.valueOf('>'), true);
                if (ref != null) {
                    t.tagPending.appendAttributeValue(ref);
                    break;
                }
                t.tagPending.appendAttributeValue('&');
                break;
            }
            case '>': {
                t.emitTagPending();
                t.transition(Data);
                break;
            }
            case '\u0000': {
                t.error(this);
                t.tagPending.appendAttributeValue('�');
                break;
            }
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            case '\"': 
            case '\'': 
            case '<': 
            case '=': 
            case '`': {
                t.error(this);
                t.tagPending.appendAttributeValue(c);
                break;
            }
            default: {
                t.tagPending.appendAttributeValue(c);
            }
        }
    }
}
