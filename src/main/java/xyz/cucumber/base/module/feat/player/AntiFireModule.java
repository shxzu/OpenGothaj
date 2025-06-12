package xyz.cucumber.base.module.feat.player;

import god.buddy.aot.BCompiler;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.PLAYER, description="Automatically put water when you are on fire", name="Anti Fire")
public class AntiFireModule
extends Mod {
    public Timer timer = new Timer();
    public boolean canWork;
    public boolean done;

    @EventListener(value=EventPriority.HIGHEST)
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        if (this.canWork && e.getType() == EventType.PRE) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(90.0f);
            this.done = false;
        }
    }

    @EventListener(value=EventPriority.HIGHEST)
    public void onLook(EventRenderRotation e) {
        if (this.canWork) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(90.0f);
        }
    }

    @EventListener(value=EventPriority.HIGHEST)
    public void onLook(EventLook e) {
        int slot;
        if (this.mc.thePlayer.isBurning()) {
            slot = InventoryUtils.getBucketSlot();
            if (slot == -1) {
                this.cancel();
                return;
            }
            this.canWork = true;
            this.mc.thePlayer.inventory.currentItem = slot;
            this.timer.reset();
        } else if (this.mc.thePlayer.isInWater()) {
            slot = InventoryUtils.getEmptyBucketSlot();
            if (slot == -1) {
                this.cancel();
                return;
            }
            if (this.timer.hasTimeElapsed(100.0, false)) {
                this.cancel();
                return;
            }
            this.canWork = true;
            this.mc.thePlayer.inventory.currentItem = slot;
        } else {
            this.cancel();
            return;
        }
        if (this.canWork) {
            EventLook event = e;
            event.setPitch(90.0f);
            RotationUtils.customRots = true;
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = 90.0f;
            if (!this.done) {
                this.mc.rightClickMouse();
            }
            this.done = true;
        }
    }

    public void cancel() {
        if (!this.canWork) {
            return;
        }
        this.done = false;
        this.canWork = false;
        RotationUtils.customRots = false;
        RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
        RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
    }
}
