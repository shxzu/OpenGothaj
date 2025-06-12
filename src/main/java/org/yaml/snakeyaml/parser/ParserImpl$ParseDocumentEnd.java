package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseDocumentEnd
implements Production {
    private ParserImpl$ParseDocumentEnd() {
    }

    @Override
    public Event produce() {
        Mark startMark;
        Token token = ParserImpl.this.scanner.peekToken();
        Mark endMark = startMark = token.getStartMark();
        boolean explicit = false;
        if (ParserImpl.this.scanner.checkToken(Token.ID.DocumentEnd)) {
            token = ParserImpl.this.scanner.getToken();
            endMark = token.getEndMark();
            explicit = true;
        }
        DocumentEndEvent event = new DocumentEndEvent(startMark, endMark, explicit);
        ParserImpl.this.state = new ParserImpl.ParseDocumentStart(ParserImpl.this, null);
        return event;
    }
}
