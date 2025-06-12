package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectFlowMappingSimpleValue
implements EmitterState {
    private Emitter$ExpectFlowMappingSimpleValue() {
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.writeIndicator(":", false, false, false);
        Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeInlineComments();
        Emitter.this.states.push(new Emitter.ExpectFlowMappingKey(Emitter.this, null));
        Emitter.this.expectNode(false, true, false);
        Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
        Emitter.this.writeInlineComments();
    }
}
