package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.emitter.EmitterState;

class Emitter$ExpectFirstBlockSequenceItem
implements EmitterState {
    private Emitter$ExpectFirstBlockSequenceItem() {
    }

    @Override
    public void expect() throws IOException {
        new Emitter.ExpectBlockSequenceItem(Emitter.this, true).expect();
    }
}
