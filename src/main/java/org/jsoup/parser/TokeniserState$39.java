package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$39
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        String value = r.consumeAttributeQuoted(true);
        if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
        } else {
            t.tagPending.setEmptyAttributeValue();
        }
        char c = r.consume();
        switch (c) {
            case '\'': {
                t.transition(AfterAttributeValue_quoted);
                break;
            }
            case '&': {
                int[] ref = t.consumeCharacterReference(Character.valueOf('\''), true);
                if (ref != null) {
                    t.tagPending.appendAttributeValue(ref);
                    break;
                }
                t.tagPending.appendAttributeValue('&');
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
            default: {
                t.tagPending.appendAttributeValue(c);
            }
        }
    }
}
