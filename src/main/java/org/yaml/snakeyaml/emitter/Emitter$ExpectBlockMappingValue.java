package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectBlockMappingValue
implements EmitterState {
    private Emitter$ExpectBlockMappingValue() {
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.writeIndent();
        Emitter.this.writeIndicator(":", true, false, true);
        Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeInlineComments();
        Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeBlockComment();
        Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(Emitter.this, false));
        Emitter.this.expectNode(false, true, false);
        Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
        Emitter.this.writeInlineComments();
    }
}
