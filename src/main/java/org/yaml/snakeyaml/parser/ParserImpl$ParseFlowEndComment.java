package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowEndComment
implements Production {
    private ParserImpl$ParseFlowEndComment() {
    }

    @Override
    public Event produce() {
        CommentEvent event = ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        if (!ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
        }
        return event;
    }
}
