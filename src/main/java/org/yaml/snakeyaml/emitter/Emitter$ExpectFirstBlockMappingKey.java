package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectFirstBlockMappingKey
implements EmitterState {
    private Emitter$ExpectFirstBlockMappingKey() {
    }

    @Override
    public void expect() throws IOException {
        new Emitter.ExpectBlockMappingKey(Emitter.this, true).expect();
    }
}
