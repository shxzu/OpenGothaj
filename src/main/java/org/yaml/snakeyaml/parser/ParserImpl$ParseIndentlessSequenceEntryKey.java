package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseIndentlessSequenceEntryKey
implements Production {
    private ParserImpl$ParseIndentlessSequenceEntryKey() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseIndentlessSequenceEntryKey();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry)) {
            BlockEntryToken token = (BlockEntryToken)ParserImpl.this.scanner.getToken();
            return new ParserImpl.ParseIndentlessSequenceEntryValue(ParserImpl.this, token).produce();
        }
        Token token = ParserImpl.this.scanner.peekToken();
        SequenceEndEvent event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
        return event;
    }
}
