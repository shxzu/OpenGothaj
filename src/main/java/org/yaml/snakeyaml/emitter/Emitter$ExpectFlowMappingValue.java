package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectFlowMappingValue
implements EmitterState {
    private Emitter$ExpectFlowMappingValue() {
    }

    @Override
    public void expect() throws IOException {
        if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth || Emitter.this.prettyFlow.booleanValue()) {
            Emitter.this.writeIndent();
        }
        Emitter.this.writeIndicator(":", true, false, false);
        Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeInlineComments();
        Emitter.this.states.push(new Emitter.ExpectFlowMappingKey(Emitter.this, null));
        Emitter.this.expectNode(false, true, false);
        Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
        Emitter.this.writeInlineComments();
    }
}
