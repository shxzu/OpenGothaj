package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowMappingKey
implements Production {
    private final boolean first;

    public ParserImpl$ParseFlowMappingKey(boolean first) {
        this.first = first;
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseFlowMappingKey(this.first);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowMappingEnd)) {
            if (!this.first) {
                if (ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry)) {
                    ParserImpl.this.scanner.getToken();
                    if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
                        ParserImpl.this.state = new ParserImpl$ParseFlowMappingKey(true);
                        return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
                    }
                } else {
                    Token token = ParserImpl.this.scanner.peekToken();
                    throw new ParserException("while parsing a flow mapping", (Mark)ParserImpl.this.marks.pop(), "expected ',' or '}', but got " + (Object)((Object)token.getTokenId()), token.getStartMark());
                }
            }
            if (ParserImpl.this.scanner.checkToken(Token.ID.Key)) {
                Token token = ParserImpl.this.scanner.getToken();
                if (!ParserImpl.this.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                    ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingValue(ParserImpl.this, null));
                    return ParserImpl.this.parseFlowNode();
                }
                ParserImpl.this.state = new ParserImpl.ParseFlowMappingValue(ParserImpl.this, null);
                return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingEmptyValue(ParserImpl.this, null));
                return ParserImpl.this.parseFlowNode();
            }
        }
        Token token = ParserImpl.this.scanner.getToken();
        MappingEndEvent event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.this.marks.pop();
        if (!ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
        } else {
            ParserImpl.this.state = new ParserImpl.ParseFlowEndComment(ParserImpl.this, null);
        }
        return event;
    }
}
