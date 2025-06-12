package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$10
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        String tagName = r.consumeTagName();
        t.tagPending.appendTagName(tagName);
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
            case '/': {
                t.transition(SelfClosingStartTag);
                break;
            }
            case '<': {
                r.unconsume();
                t.error(this);
            }
            case '>': {
                t.emitTagPending();
                t.transition(Data);
                break;
            }
            case '\u0000': {
                t.tagPending.appendTagName(replacementStr);
                break;
            }
            case '￿': {
                t.eofError(this);
                t.transition(Data);
                break;
            }
            default: {
                t.tagPending.appendTagName(c);
            }
        }
    }
}
