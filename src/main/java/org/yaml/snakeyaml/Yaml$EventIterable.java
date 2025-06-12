package org.yaml.snakeyaml;

import java.util.Iterator;
import org.yaml.snakeyaml.events.Event;

class Yaml$EventIterable
implements Iterable<Event> {
    private final Iterator<Event> iterator;

    public Yaml$EventIterable(Iterator<Event> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<Event> iterator() {
        return this.iterator;
    }
}
