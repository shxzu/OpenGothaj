package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventMoveFlying
extends Event {
    private float yaw;
    private float strafe;
    private float forward;
    private float friction;

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public float getForward() {
        return this.forward;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getFriction() {
        return this.friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public EventMoveFlying(float yaw, float strafe, float forward, float friction) {
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }
}
