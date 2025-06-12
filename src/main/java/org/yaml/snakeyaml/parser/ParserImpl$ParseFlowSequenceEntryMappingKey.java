package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowSequenceEntryMappingKey
implements Production {
    private ParserImpl$ParseFlowSequenceEntryMappingKey() {
    }

    @Override
    public Event produce() {
        Token token = ParserImpl.this.scanner.getToken();
        if (!ParserImpl.this.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
            ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingValue(ParserImpl.this, null));
            return ParserImpl.this.parseFlowNode();
        }
        ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingValue(ParserImpl.this, null);
        return ParserImpl.this.processEmptyScalar(token.getEndMark());
    }
}
