package xyz.cucumber.base.events;

import java.lang.reflect.Method;

public class EventBus$Callable {
    private Object object;
    private Method method;

    public EventBus$Callable(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object getObject() {
        return this.object;
    }

    public Method getMethod() {
        return this.method;
    }
}
