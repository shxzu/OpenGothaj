package org.yaml.snakeyaml.comments;

import java.util.AbstractQueue;
import java.util.Iterator;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.Parser;

class CommentEventsCollector$1
extends AbstractQueue<Event> {
    final Parser val$parser;

    CommentEventsCollector$1(Parser parser) {
        this.val$parser = parser;
    }

    @Override
    public boolean offer(Event e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Event poll() {
        return this.val$parser.getEvent();
    }

    @Override
    public Event peek() {
        return this.val$parser.peekEvent();
    }

    @Override
    public Iterator<Event> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }
}
