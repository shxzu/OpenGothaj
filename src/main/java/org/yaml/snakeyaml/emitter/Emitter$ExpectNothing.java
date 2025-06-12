package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectNothing
implements EmitterState {
    private Emitter$ExpectNothing() {
    }

    @Override
    public void expect() throws IOException {
        throw new EmitterException("expecting nothing, but got " + Emitter.this.event);
    }
}
