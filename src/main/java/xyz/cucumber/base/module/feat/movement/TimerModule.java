package xyz.cucumber.base.module.feat.movement;

import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to change game speed", name="Timer", key=0)
public class TimerModule
extends Mod {
    public BooleanSettings jump = new BooleanSettings("Jump", false);
    public NumberSettings timer = new NumberSettings("Timer", 1.0, 0.0, 10.0, 0.1);
    public Timer startDelay = new Timer();

    public TimerModule() {
        this.addSettings(this.jump, this.timer);
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }

    @Override
    public void onEnable() {
        this.mc.timer.timerSpeed = 1.0f;
        this.startDelay.reset();
    }

    @EventListener
    public void onGameLoop(EventGameLoop e) {
        if (Keyboard.isKeyDown((int)this.getKey()) && this.startDelay.hasTimeElapsed(150.0, false) && (double)this.mc.timer.timerSpeed == this.timer.getValue()) {
            this.mc.timer.timerSpeed = 1.0f;
            this.startDelay.setTime(99999999L);
            return;
        }
        if (this.startDelay.getTime() > 99999999L) {
            return;
        }
        this.setInfo(String.valueOf(this.timer.getValue()));
        this.mc.timer.timerSpeed = MovementUtils.isMoving() ? (float)this.timer.getValue() : 1.0f;
    }

    @EventListener
    public void onMoveButton(EventMoveButton e) {
        if (MovementUtils.isMoving() && this.jump.isEnabled()) {
            e.jump = true;
        }
    }
}
