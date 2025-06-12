package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowMappingFirstKey
implements Production {
    private ParserImpl$ParseFlowMappingFirstKey() {
    }

    @Override
    public Event produce() {
        Token token = ParserImpl.this.scanner.getToken();
        ParserImpl.this.marks.push(token.getStartMark());
        return new ParserImpl.ParseFlowMappingKey(ParserImpl.this, true).produce();
    }
}
