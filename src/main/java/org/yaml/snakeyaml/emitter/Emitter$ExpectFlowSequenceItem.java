package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;

class Emitter$ExpectFlowSequenceItem
implements EmitterState {
    private Emitter$ExpectFlowSequenceItem() {
    }

    @Override
    public void expect() throws IOException {
        if (Emitter.this.event instanceof SequenceEndEvent) {
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
            Emitter.this.flowLevel--;
            if (Emitter.this.canonical.booleanValue()) {
                Emitter.this.writeIndicator(",", false, false, false);
                Emitter.this.writeIndent();
            } else if (Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.writeIndicator("]", false, false, false);
            Emitter.this.inlineCommentsCollector.collectEvents();
            Emitter.this.writeInlineComments();
            if (Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.state = (EmitterState)Emitter.this.states.pop();
        } else if (Emitter.this.event instanceof CommentEvent) {
            Emitter.this.event = Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
        } else {
            Emitter.this.writeIndicator(",", false, false, false);
            Emitter.this.writeBlockComment();
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.states.push(new Emitter$ExpectFlowSequenceItem());
            Emitter.this.expectNode(false, false, false);
            Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
            Emitter.this.writeInlineComments();
        }
    }
}
