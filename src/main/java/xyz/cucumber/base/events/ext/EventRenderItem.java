package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventRenderItem
extends Event {
    private float f;
    private float f1;

    public EventRenderItem(float f, float f1) {
        this.f = f;
        this.f1 = f1;
    }

    public float getF1() {
        return this.f1;
    }

    public void setF1(float f1) {
        this.f1 = f1;
    }

    public float getF() {
        return this.f;
    }

    public void setF(float f) {
        this.f = f;
    }
}
