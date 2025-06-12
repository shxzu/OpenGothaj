package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.Token;

final class HtmlTreeBuilderState$10
extends HtmlTreeBuilderState {
    @Override
    boolean process(Token t, HtmlTreeBuilder tb) {
        Token.Character c;
        if (t.type == Token.TokenType.Character) {
            c = t.asCharacter();
            if (c.getData().equals(nullString)) {
                tb.error(this);
                return false;
            }
        } else {
            if (tb.getPendingTableCharacters().size() > 0) {
                for (String character : tb.getPendingTableCharacters()) {
                    if (!HtmlTreeBuilderState.isWhitespace(character)) {
                        tb.error(this);
                        if (StringUtil.inSorted(tb.currentElement().normalName(), HtmlTreeBuilderState.Constants.InTableFoster)) {
                            tb.setFosterInserts(true);
                            tb.process(new Token.Character().data(character), InBody);
                            tb.setFosterInserts(false);
                            continue;
                        }
                        tb.process(new Token.Character().data(character), InBody);
                        continue;
                    }
                    tb.insert(new Token.Character().data(character));
                }
                tb.newPendingTableCharacters();
            }
            tb.transition(tb.originalState());
            return tb.process(t);
        }
        tb.getPendingTableCharacters().add(c.getData());
        return true;
    }
}
