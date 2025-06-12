package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.MappingEndEvent;

class Emitter$ExpectFirstFlowMappingKey
implements EmitterState {
    private Emitter$ExpectFirstFlowMappingKey() {
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeBlockComment();
        if (Emitter.this.event instanceof MappingEndEvent) {
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
            Emitter.this.flowLevel--;
            Emitter.this.writeIndicator("}", false, false, false);
            Emitter.this.inlineCommentsCollector.collectEvents();
            Emitter.this.writeInlineComments();
            Emitter.this.state = (EmitterState)Emitter.this.states.pop();
        } else {
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            if (!Emitter.this.canonical.booleanValue() && Emitter.this.checkSimpleKey()) {
                Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue(Emitter.this, null));
                Emitter.this.expectNode(false, true, true);
            } else {
                Emitter.this.writeIndicator("?", true, false, false);
                Emitter.this.states.push(new Emitter.ExpectFlowMappingValue(Emitter.this, null));
                Emitter.this.expectNode(false, true, false);
            }
        }
    }
}
