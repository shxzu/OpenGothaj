package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$17
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        switch (r.consume()) {
            case '/': {
                t.createTempBuffer();
                t.transition(ScriptDataEndTagOpen);
                break;
            }
            case '!': {
                t.emit("<!");
                t.transition(ScriptDataEscapeStart);
                break;
            }
            case '￿': {
                t.emit("<");
                t.eofError(this);
                t.transition(Data);
                break;
            }
            default: {
                t.emit("<");
                r.unconsume();
                t.transition(ScriptData);
            }
        }
    }
}
