package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;

final class TokeniserState$8
extends TokeniserState {
    @Override
    void read(Tokeniser t, CharacterReader r) {
        switch (r.current()) {
            case '!': {
                t.advanceTransition(MarkupDeclarationOpen);
                break;
            }
            case '/': {
                t.advanceTransition(EndTagOpen);
                break;
            }
            case '?': {
                t.createBogusCommentPending();
                t.transition(BogusComment);
                break;
            }
            default: {
                if (r.matchesAsciiAlpha()) {
                    t.createTagPending(true);
                    t.transition(TagName);
                    break;
                }
                t.error(this);
                t.emit('<');
                t.transition(Data);
            }
        }
    }
}
