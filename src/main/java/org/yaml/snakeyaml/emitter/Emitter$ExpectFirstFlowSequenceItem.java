package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;

class Emitter$ExpectFirstFlowSequenceItem
implements EmitterState {
    private Emitter$ExpectFirstFlowSequenceItem() {
    }

    @Override
    public void expect() throws IOException {
        if (Emitter.this.event instanceof SequenceEndEvent) {
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
            Emitter.this.flowLevel--;
            Emitter.this.writeIndicator("]", false, false, false);
            Emitter.this.inlineCommentsCollector.collectEvents();
            Emitter.this.writeInlineComments();
            Emitter.this.state = (EmitterState)Emitter.this.states.pop();
        } else if (Emitter.this.event instanceof CommentEvent) {
            Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
            Emitter.this.writeBlockComment();
        } else {
            if (Emitter.this.canonical.booleanValue() || Emitter.this.column > Emitter.this.bestWidth && Emitter.this.splitLines || Emitter.this.prettyFlow.booleanValue()) {
                Emitter.this.writeIndent();
            }
            Emitter.this.states.push(new Emitter.ExpectFlowSequenceItem(Emitter.this, null));
            Emitter.this.expectNode(false, false, false);
            Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEvents(Emitter.this.event);
            Emitter.this.writeInlineComments();
        }
    }
}
