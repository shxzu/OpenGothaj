package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseImplicitDocumentStart
implements Production {
    private ParserImpl$ParseImplicitDocumentStart() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseImplicitDocumentStart();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
            Mark startMark;
            Token token = ParserImpl.this.scanner.peekToken();
            Mark endMark = startMark = token.getStartMark();
            DocumentStartEvent event = new DocumentStartEvent(startMark, endMark, false, null, null);
            ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd(ParserImpl.this, null));
            ParserImpl.this.state = new ParserImpl.ParseBlockNode(ParserImpl.this, null);
            return event;
        }
        return new ParserImpl.ParseDocumentStart(ParserImpl.this, null).produce();
    }
}
