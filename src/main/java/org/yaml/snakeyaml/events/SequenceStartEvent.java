package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.Event;

public final class SequenceStartEvent
extends CollectionStartEvent {
    public SequenceStartEvent(String anchor, String tag, boolean implicit, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
        super(anchor, tag, implicit, startMark, endMark, flowStyle);
    }

    @Override
    public Event.ID getEventId() {
        return Event.ID.SequenceStart;
    }
}
