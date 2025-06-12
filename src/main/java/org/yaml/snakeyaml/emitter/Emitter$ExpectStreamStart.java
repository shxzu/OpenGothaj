package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.EmitterState;
import org.yaml.snakeyaml.events.StreamStartEvent;

class Emitter$ExpectStreamStart
implements EmitterState {
    private Emitter$ExpectStreamStart() {
    }

    @Override
    public void expect() throws IOException {
        if (!(Emitter.this.event instanceof StreamStartEvent)) {
            throw new EmitterException("expected StreamStartEvent, but got " + Emitter.this.event);
        }
        Emitter.this.writeStreamStart();
        Emitter.this.state = new Emitter.ExpectFirstDocumentStart(Emitter.this, null);
    }
}
