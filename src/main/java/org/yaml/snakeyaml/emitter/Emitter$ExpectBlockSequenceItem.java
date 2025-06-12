package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;

class Emitter$ExpectBlockSequenceItem
implements EmitterState {
    private final boolean first;

    public Emitter$ExpectBlockSequenceItem(boolean first) {
        this.first = first;
    }

    @Override
    public void expect() throws IOException {
        if (!this.first && Emitter.this.event instanceof SequenceEndEvent) {
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
            Emitter.this.state = (EmitterState)Emitter.this.states.pop();
        } else if (Emitter.this.event instanceof CommentEvent) {
            Emitter.this.blockCommentsCollector.collectEvents(Emitter.this.event);
        } else {
            Emitter.this.writeIndent();
            if (!Emitter.this.indentWithIndicator || this.first) {
                Emitter.this.writeWhitespace(Emitter.this.indicatorIndent);
            }
            Emitter.this.writeIndicator("-", true, false, true);
            if (Emitter.this.indentWithIndicator && this.first) {
                Emitter.this.indent = Emitter.this.indent + Emitter.this.indicatorIndent;
            }
            if (!Emitter.this.blockCommentsCollector.isEmpty()) {
                Emitter.this.increaseIndent(false, false);
                Emitter.this.writeBlockComment();
                if (Emitter.this.event instanceof ScalarEvent) {
                    Emitter.this.analysis = Emitter.this.analyzeScalar(((ScalarEvent)Emitter.this.event).getValue());
                    if (!Emitter.this.analysis.isEmpty()) {
                        Emitter.this.writeIndent();
                    }
                }
                Emitter.this.indent = (Integer)Emitter.this.indents.pop();
            }
            Emitter.this.states.push(new Emitter$ExpectBlockSequenceItem(false));
            Emitter.this.expectNode(false, false, false);
            Emitter.this.inlineCommentsCollector.collectEvents();
            Emitter.this.writeInlineComments();
        }
    }
}
