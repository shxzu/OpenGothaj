package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.MappingEndEvent;

class Emitter$ExpectBlockMappingKey
implements EmitterState {
    private final boolean first;

    public Emitter$ExpectBlockMappingKey(boolean first) {
        this.first = first;
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeBlockComment();
        if (!this.first && Emitter.this.event instanceof MappingEndEvent) {
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
            Emitter.this.state = (EmitterState)Emitter.this.states.pop();
        } else {
            Emitter.this.writeIndent();
            if (Emitter.this.checkSimpleKey()) {
                Emitter.this.states.push(new Emitter.ExpectBlockMappingSimpleValue(Emitter.this, null));
                Emitter.this.expectNode(false, true, true);
            } else {
                Emitter.this.writeIndicator("?", true, false, true);
                Emitter.this.states.push(new Emitter.ExpectBlockMappingValue(Emitter.this, null));
                Emitter.this.expectNode(false, true, false);
            }
        }
    }
}
