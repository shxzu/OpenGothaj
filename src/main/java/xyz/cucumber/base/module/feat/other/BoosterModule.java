package xyz.cucumber.base.module.feat.other;

import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(category=Category.OTHER, description="improves your preformance", name="Booster", priority=ArrayPriority.LOW)
public class BoosterModule
extends Mod {
    @EventListener
    public void onTick(EventTick e) {
        if (this.mc.thePlayer.ticksExisted % 600 == 0) {
            System.gc();
            Runtime.getRuntime().gc();
        }
    }
}
