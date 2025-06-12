package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;

class ParserImpl$ParseFlowMappingEmptyValue
implements Production {
    private ParserImpl$ParseFlowMappingEmptyValue() {
    }

    @Override
    public Event produce() {
        ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
        return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
    }
}
