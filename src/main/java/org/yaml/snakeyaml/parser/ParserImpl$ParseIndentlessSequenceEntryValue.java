package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseIndentlessSequenceEntryValue
implements Production {
    BlockEntryToken token;

    public ParserImpl$ParseIndentlessSequenceEntryValue(BlockEntryToken token) {
        this.token = token;
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseIndentlessSequenceEntryValue(this.token);
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
            ParserImpl.this.states.push(new ParserImpl.ParseIndentlessSequenceEntryKey(ParserImpl.this, null));
            return new ParserImpl.ParseBlockNode(ParserImpl.this, null).produce();
        }
        ParserImpl.this.state = new ParserImpl.ParseIndentlessSequenceEntryKey(ParserImpl.this, null);
        return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
    }
}
