package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectFirstDocumentStart
implements EmitterState {
    private Emitter$ExpectFirstDocumentStart() {
    }

    @Override
    public void expect() throws IOException {
        new Emitter.ExpectDocumentStart(Emitter.this, true).expect();
    }
}
