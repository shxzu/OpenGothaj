package xyz.cucumber.base.utils.animations;

import xyz.cucumber.base.utils.animations.AnimationDirection;

public class Animation {
    private AnimationDirection direction;
    private double animation;
    private int delay;
    private int finishTime;

    public Animation(AnimationDirection direction, int delay, int states) {
        this.direction = direction;
        this.delay = delay;
        this.finishTime = (int)(System.nanoTime() / 1000000L + (long)delay);
    }

    public void reset() {
        this.finishTime = (int)(System.nanoTime() / 1000000L + (long)this.delay);
        this.animation = 0.0;
        this.direction = AnimationDirection.PRESCALING;
    }

    public float getAnimation(float finalValue, int speed) {
        if (this.direction == AnimationDirection.PRESCALING) {
            if (this.animation < 1.005) {
                float finals = (float)((this.animation * 7.0 + (double)finalValue * 1.02) / 8.0);
                this.animation = finals;
                return finals;
            }
            this.direction = AnimationDirection.CORRECTING;
        } else if (this.direction == AnimationDirection.CORRECTING) {
            if (this.animation > 1.005) {
                float finals = (float)((this.animation * 4.0 + (double)finalValue) / 5.0);
                this.animation = finals;
                return finals;
            }
            this.direction = AnimationDirection.FINAL;
            this.direction = AnimationDirection.FINAL;
        } else {
            return finalValue;
        }
        return finalValue;
    }

    public void changeState() {
        this.direction = this.direction == AnimationDirection.PRESCALING ? AnimationDirection.CORRECTING : (this.direction == AnimationDirection.CORRECTING ? AnimationDirection.FINAL : AnimationDirection.PRESCALING);
    }

    public AnimationDirection getDirection() {
        return this.direction;
    }

    public void setDirection(AnimationDirection direction) {
        this.direction = direction;
    }

    public long getDelay() {
        return this.delay;
    }
}
