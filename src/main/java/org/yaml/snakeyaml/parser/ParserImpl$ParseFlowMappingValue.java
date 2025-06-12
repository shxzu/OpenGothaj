package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowMappingValue
implements Production {
    private ParserImpl$ParseFlowMappingValue() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Value)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (!ParserImpl.this.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false));
                return ParserImpl.this.parseFlowNode();
            }
            ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
        Token token = ParserImpl.this.scanner.peekToken();
        return ParserImpl.this.processEmptyScalar(token.getStartMark());
    }
}
