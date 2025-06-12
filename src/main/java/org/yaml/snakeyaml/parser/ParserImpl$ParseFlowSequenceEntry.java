package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowSequenceEntry
implements Production {
    private final boolean first;

    public ParserImpl$ParseFlowSequenceEntry(boolean first) {
        this.first = first;
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseFlowSequenceEntry(this.first);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
            if (!this.first) {
                if (ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry)) {
                    ParserImpl.this.scanner.getToken();
                    if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
                        ParserImpl.this.state = new ParserImpl$ParseFlowSequenceEntry(true);
                        return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
                    }
                } else {
                    Token token = ParserImpl.this.scanner.peekToken();
                    throw new ParserException("while parsing a flow sequence", (Mark)ParserImpl.this.marks.pop(), "expected ',' or ']', but got " + (Object)((Object)token.getTokenId()), token.getStartMark());
                }
            }
            if (ParserImpl.this.scanner.checkToken(Token.ID.Key)) {
                Token token = ParserImpl.this.scanner.peekToken();
                MappingStartEvent event = new MappingStartEvent(null, null, true, token.getStartMark(), token.getEndMark(), DumperOptions.FlowStyle.FLOW);
                ParserImpl.this.state = new ParserImpl$ParseFlowSequenceEntryMappingKey(ParserImpl.this, null);
                return event;
            }
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                ParserImpl.this.states.push(new ParserImpl$ParseFlowSequenceEntry(false));
                return ParserImpl.this.parseFlowNode();
            }
        }
        Token token = ParserImpl.this.scanner.getToken();
        SequenceEndEvent event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
        if (!ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
        } else {
            ParserImpl.this.state = new ParserImpl.ParseFlowEndComment(ParserImpl.this, null);
        }
        ParserImpl.this.marks.pop();
        return event;
    }
}
