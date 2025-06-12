package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$13
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        if (r.matchesAsciiAlpha()) {
            String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
            return;
        }
        char c = r.consume();
        switch (c) {
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                if (t.isAppropriateEndTagToken()) {
                    t.transition(BeforeAttributeName);
                    break;
                }
                this.anythingElse(t, r);
                break;
            }
            case '/': {
                if (t.isAppropriateEndTagToken()) {
                    t.transition(SelfClosingStartTag);
                    break;
                }
                this.anythingElse(t, r);
                break;
            }
            case '>': {
                if (t.isAppropriateEndTagToken()) {
                    t.emitTagPending();
                    t.transition(Data);
                    break;
                }
                this.anythingElse(t, r);
                break;
            }
            default: {
                this.anythingElse(t, r);
            }
        }
    }

    private void anythingElse(Tokeniser t, CharacterReader r) {
        t.emit("</");
        t.emit(t.dataBuffer);
        r.unconsume();
        t.transition(Rcdata);
    }
}
