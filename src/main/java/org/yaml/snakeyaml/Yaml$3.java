package org.yaml.snakeyaml;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.Parser;

class Yaml$3
implements Iterator<Event> {
    final Parser val$parser;

    Yaml$3(Parser parser) {
        this.val$parser = parser;
    }

    @Override
    public boolean hasNext() {
        return this.val$parser.peekEvent() != null;
    }

    @Override
    public Event next() {
        Event event = this.val$parser.getEvent();
        if (event != null) {
            return event;
        }
        throw new NoSuchElementException("No Event is available.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
