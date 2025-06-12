package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.DocumentEndEvent;

class Emitter$ExpectDocumentEnd
implements EmitterState {
    private Emitter$ExpectDocumentEnd() {
    }

    @Override
    public void expect() throws IOException {
        Emitter.this.event = Emitter.this.blockCommentsCollector.collectEventsAndPoll(Emitter.this.event);
        Emitter.this.writeBlockComment();
        if (Emitter.this.event instanceof DocumentEndEvent) {
            Emitter.this.writeIndent();
            if (((DocumentEndEvent)Emitter.this.event).getExplicit()) {
                Emitter.this.writeIndicator("...", true, false, false);
                Emitter.this.writeIndent();
            }
        } else {
            throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.this.event);
        }
        Emitter.this.flushStream();
        Emitter.this.state = new Emitter.ExpectDocumentStart(Emitter.this, false);
    }
}
