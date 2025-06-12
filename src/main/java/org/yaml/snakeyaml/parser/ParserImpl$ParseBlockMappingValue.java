package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseBlockMappingValue
implements Production {
    private ParserImpl$ParseBlockMappingValue() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Value)) {
            Token token = ParserImpl.this.scanner.getToken();
            if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
                ParserImpl.this.state = new ParserImpl$ParseBlockMappingValueComment(ParserImpl.this, null);
                return ParserImpl.this.state.produce();
            }
            if (!ParserImpl.this.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null));
                return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            }
            ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null);
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        if (ParserImpl.this.scanner.checkToken(Token.ID.Scalar)) {
            ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null));
            return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
        }
        ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null);
        Token token = ParserImpl.this.scanner.peekToken();
        return ParserImpl.this.processEmptyScalar(token.getStartMark());
    }
}
