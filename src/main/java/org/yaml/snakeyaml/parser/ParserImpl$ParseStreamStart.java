package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.StreamStartToken;

class ParserImpl$ParseStreamStart
implements Production {
    private ParserImpl$ParseStreamStart() {
    }

    @Override
    public Event produce() {
        StreamStartToken token = (StreamStartToken)ParserImpl.this.scanner.getToken();
        StreamStartEvent event = new StreamStartEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.this.state = new ParserImpl.ParseImplicitDocumentStart(ParserImpl.this, null);
        return event;
    }
}
