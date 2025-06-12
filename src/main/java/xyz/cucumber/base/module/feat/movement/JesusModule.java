package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventPlayerPush;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to move faster in water", name="Jesus", key=0)
public class JesusModule
extends Mod {
    public BooleanSettings noPush = new BooleanSettings("No Push", true);
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Intave"});
    public NumberSettings intaveDelay = new NumberSettings("Intave Delay", 2.0, 1.0, 10.0, 1.0);

    public JesusModule() {
        this.addSettings(this.mode, this.intaveDelay, this.noPush);
    }

    @EventListener
    public void onPush(EventPlayerPush e) {
        if (this.noPush.isEnabled()) {
            e.setCancelled(true);
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        if (e.getType() != EventType.PRE) {
            return;
        }
        switch (this.mode.getMode().toLowerCase()) {
            case "intave": {
                this.setInfo(String.valueOf(this.mode.getMode()));
                if (!this.mc.thePlayer.isInWater() || (double)this.mc.thePlayer.ticksExisted % this.intaveDelay.getValue() != 0.0 || !MovementUtils.isMoving() || this.mc.thePlayer.hurtTime != 0) break;
                MovementUtils.strafe(0.25f, (float)MovementUtils.getDirection(RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw));
            }
        }
    }
}
