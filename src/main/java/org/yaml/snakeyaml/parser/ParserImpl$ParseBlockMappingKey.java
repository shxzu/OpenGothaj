package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseBlockMappingKey
implements Production {
    private ParserImpl$ParseBlockMappingKey() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseBlockMappingKey();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (ParserImpl.this.scanner.checkToken(Token.ID.Key)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingValue(ParserImpl.this, null));
                return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            }
            ParserImpl.this.state = new ParserImpl.ParseBlockMappingValue(ParserImpl.this, null);
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEnd)) {
            Token token = ParserImpl.this.scanner.peekToken();
            throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found '" + (Object)((Object)token.getTokenId()) + "'", token.getStartMark());
        }
        Token token = ParserImpl.this.scanner.getToken();
        MappingEndEvent event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
        ParserImpl.this.marks.pop();
        return event;
    }
}
