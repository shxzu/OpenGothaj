package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectBlockMappingSimpleValue
implements EmitterState {
    private Emitter$ExpectBlockMappingSimpleValue() {
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.writeIndicator(":", false, false, false);
        Emitter.this.event = Emitter.this.inlineCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        if (!Emitter.this.isFoldedOrLiteral(Emitter.this.event) && Emitter.this.writeInlineComments()) {
            Emitter.this.increaseIndent(true, false);
            Emitter.this.writeIndent();
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
        }
        Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        if (!Emitter.this.blockCommentsCollector.isEmpty()) {
            Emitter.this.increaseIndent(true, false);
            Emitter.this.writeBlockComment();
            Emitter.this.writeIndent();
            Emitter.this.indent = (Integer)Emitter.this.indents.pop();
        }
        Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(Emitter.this, false));
        Emitter.this.expectNode(false, true, false);
        Emitter.this.inlineCommentsCollector.collectEvents();
        Emitter.this.writeInlineComments();
    }
}
