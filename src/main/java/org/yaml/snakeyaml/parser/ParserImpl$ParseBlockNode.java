package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.Production;

class ParserImpl$ParseBlockNode
implements Production {
    private ParserImpl$ParseBlockNode() {
    }

    @Override
    public Event produce() {
        return ParserImpl.this.parseNode(true, false);
    }
}
