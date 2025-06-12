package org.yaml.snakeyaml.parser;

import java.util.List;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.parser.Production;
import org.yaml.snakeyaml.tokens.CommentToken;

class ParserImpl$ParseBlockMappingValueCommentList
implements Production {
    List<CommentToken> tokens;

    public ParserImpl$ParseBlockMappingValueCommentList(List<CommentToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Event produce() {
        if (!this.tokens.isEmpty()) {
            return ParserImpl.this.produceCommentEvent(this.tokens.remove(0));
        }
        return new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null).produce();
    }
}
