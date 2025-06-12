package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$35
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        String name = r.consumeToAnySorted(attributeNameCharsSorted);
        t.tagPending.appendAttributeName(name);
        char c = r.consume();
        switch (c) {
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                t.transition(AfterAttributeName);
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
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            case '\"': 
            case '\'': 
            case '<': {
                t.error(this);
                t.tagPending.appendAttributeName(c);
                break;
            }
            default: {
                t.tagPending.appendAttributeName(c);
            }
        }
    }
}
