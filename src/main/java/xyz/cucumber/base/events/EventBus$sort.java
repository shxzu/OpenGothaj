package xyz.cucumber.base.events;

import java.util.Comparator;
import xyz.cucumber.base.events.EventBus;
import xyz.cucumber.base.events.EventListener;

public class EventBus$sort
implements Comparator<EventBus.Callable> {
    @Override
    public int compare(EventBus.Callable o1, EventBus.Callable o2) {
        if (o1.getMethod().getAnnotation(EventListener.class).value().getValue() < o2.getMethod().getAnnotation(EventListener.class).value().getValue()) {
            return -1;
        }
        if (o1.getMethod().getAnnotation(EventListener.class).value().getValue() == o2.getMethod().getAnnotation(EventListener.class).value().getValue()) {
            return 0;
        }
        return 1;
    }
}
