package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventKeyPress
extends Event {
    private int key;

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public EventKeyPress(int key) {
        this.key = key;
    }
}
