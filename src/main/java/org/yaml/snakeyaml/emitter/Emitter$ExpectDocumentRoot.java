package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.DocumentEndEvent;

class Emitter$ExpectDocumentRoot
implements EmitterState {
    private Emitter$ExpectDocumentRoot() {
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        if (!Emitter.this.blockCommentsCollector.isEmpty()) {
            Emitter.this.writeBlockComment();
            if (Emitter.this.event instanceof DocumentEndEvent) {
                new Emitter.ExpectDocumentEnd(Emitter.this, null).expect();
                return;
            }
        }
        Emitter.this.states.push(new Emitter.ExpectDocumentEnd(Emitter.this, null));
        Emitter.this.expectNode(true, false, false);
    }
}
