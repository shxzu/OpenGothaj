package org.yaml.snakeyaml.parser;

import java.util.LinkedList;
import java.util.List;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseBlockMappingValueComment
implements Production {
    List<CommentToken> tokens = new LinkedList<CommentToken>();

    private ParserImpl$ParseBlockMappingValueComment() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            this.tokens.add((CommentToken)ParserImpl.this.scanner.getToken());
            return this.produce();
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
            if (!this.tokens.isEmpty()) {
                return ParserImpl.this.produceCommentEvent(this.tokens.remove(0));
            }
            ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null));
            return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
        }
        ParserImpl.this.state = new ParserImpl$ParseBlockMappingValueCommentList(ParserImpl.this, this.tokens);
        return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
    }
}
