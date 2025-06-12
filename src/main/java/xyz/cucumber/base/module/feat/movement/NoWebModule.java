package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to move faster in web", name="No Web", key=0)
public class NoWebModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Intave", "Vanilla"});
    public BooleanSettings shift = new BooleanSettings("k", false);
    public boolean sneaking;

    public NoWebModule() {
        this.addSettings(this.mode, this.shift);
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onTick(EventTick e) {
        this.setInfo(this.mode.getMode());
        if (this.mc.thePlayer.isInWeb) {
            if (this.mc.thePlayer.isInWeb && this.shift.isEnabled()) {
                this.mc.gameSettings.keyBindSneak.pressed = true;
                this.sneaking = true;
            }
        } else if (this.sneaking) {
            this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindSneak.getKeyCode());
            this.sneaking = false;
        }
        switch (this.mode.getMode().toLowerCase()) {
            case "intave": {
                if (!MovementUtils.isMoving() || !this.mc.thePlayer.isInWeb) break;
                this.mc.gameSettings.keyBindJump.pressed = false;
                if (!this.mc.thePlayer.onGround) {
                    this.mc.timer.timerSpeed = 1.0f;
                    if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                        MovementUtils.strafe(0.65f, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw));
                    } else if (this.mc.thePlayer.ticksExisted % 5 == 0) {
                        MovementUtils.strafe(0.65f, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw));
                    }
                } else {
                    MovementUtils.strafe(0.35f, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw));
                    this.mc.thePlayer.jump();
                    this.mc.thePlayer.jump();
                    this.mc.thePlayer.jump();
                }
                if (this.mc.thePlayer.isSprinting()) break;
                this.mc.thePlayer.motionX *= 0.75;
                this.mc.thePlayer.motionZ *= 0.75;
                break;
            }
            case "vanilla": {
                if (!MovementUtils.isMoving() || !this.mc.thePlayer.isInWeb) break;
                this.mc.thePlayer.isInWeb = false;
            }
        }
    }
}
