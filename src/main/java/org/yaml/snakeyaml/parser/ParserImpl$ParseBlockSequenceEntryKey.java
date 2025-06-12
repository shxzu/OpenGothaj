package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.BlockEntryToken;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseBlockSequenceEntryKey
implements Production {
    private ParserImpl$ParseBlockSequenceEntryKey() {
    }

    @Override
    public Event produce() {
        if (ParserImpl.this.scanner.checkToken(Token.ID.Comment)) {
            ParserImpl.this.state = new ParserImpl$ParseBlockSequenceEntryKey();
            return ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
        }
        if (ParserImpl.this.scanner.checkToken(Token.ID.BlockEntry)) {
            BlockEntryToken token = (BlockEntryToken)ParserImpl.this.scanner.getToken();
            return new ParserImpl.ParseBlockSequenceEntryValue(ParserImpl.this, token).produce();
        }
        if (!ParserImpl.this.scanner.checkToken(Token.ID.BlockEnd)) {
            Token token = ParserImpl.this.scanner.peekToken();
            throw new ParserException("while parsing a block collection", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found '" + (Object)((Object)token.getTokenId()) + "'", token.getStartMark());
        }
        Token token = ParserImpl.this.scanner.getToken();
        SequenceEndEvent event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
        ParserImpl.this.marks.pop();
        return event;
    }
}
