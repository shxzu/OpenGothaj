package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowSequenceEntryMappingValue
implements Production {
    private ParserImpl$ParseFlowSequenceEntryMappingValue() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Value)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null));
                return ParserImpl.this.parseFlowNode();
            }
            ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null);
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null);
        Token token = ParserImpl.this.scanner.peekToken();
        return ParserImpl.this.processEmptyScalar(token.getStartMark());
    }
}
