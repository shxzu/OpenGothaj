package xyz.cucumber.base.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import xyz.cucumber.base.events.EventPriority;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface EventListener {
    public EventPriority value() default EventPriority.MEDIUM;
}
