package org.yaml.snakeyaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.events.Event;

class Yaml$SilentEmitter
implements Emitable {
    private final List<Event> events = new ArrayList<Event>(100);

    private Yaml$SilentEmitter() {
    }

    public List<Event> getEvents() {
        return this.events;
    }

    @Override
    public void emit(Event event) throws IOException {
        this.events.add(event);
    }
}
