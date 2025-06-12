package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.parser.VersionTagsTuple;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseDocumentStart
implements Production {
    private ParserImpl$ParseDocumentStart() {
    }

    @Override
    public Event produce() {
        Token token;
        while (ParserImpl.this.scanner.checkToken(Token.ID.DocumentEnd)) {
            ParserImpl.this.scanner.getToken();
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.StreamEnd)) {
            ParserImpl.this.scanner.resetDocumentIndex();
            token = ParserImpl.this.scanner.peekToken();
            Mark startMark = token.getStartMark();
            VersionTagsTuple tuple = ParserImpl.this.processDirectives();
            while (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.this.scanner.getToken();
            }
            if (!ParserImpl.this.scanner.checkToken(Token.ID.StreamEnd)) {
                if (!ParserImpl.this.scanner.checkToken(Token.ID.DocumentStart)) {
                    throw new ParserException(null, null, "expected '<document start>', but found '" + (Object)((Object)ParserImpl.this.scanner.peekToken().getTokenId()) + "'", ParserImpl.this.scanner.peekToken().getStartMark());
                }
                token = ParserImpl.this.scanner.getToken();
                Mark endMark = token.getEndMark();
                DocumentStartEvent event = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
                ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd(ParserImpl.this, null));
                ParserImpl.this.state = new ParserImpl.ParseDocumentContent(ParserImpl.this, null);
                return event;
            }
        }
        token = (StreamEndToken)ParserImpl.this.scanner.getToken();
        StreamEndEvent event = new StreamEndEvent(token.getStartMark(), token.getEndMark());
        if (!ParserImpl.this.states.isEmpty()) {
            throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.this.states);
        }
        if (!ParserImpl.this.marks.isEmpty()) {
            throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.this.marks);
        }
        ParserImpl.this.state = null;
        return event;
    }
}
