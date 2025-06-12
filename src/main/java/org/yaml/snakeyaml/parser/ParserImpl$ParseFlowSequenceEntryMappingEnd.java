package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowSequenceEntryMappingEnd
implements Production {
    private ParserImpl$ParseFlowSequenceEntryMappingEnd() {
    }

    @Override
    public Event produce() {
        ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntry(ParserImpl.this, false);
        Token token = ParserImpl.this.scanner.peekToken();
        return new MappingEndEvent(token.getStartMark(), token.getEndMark());
    }
}
