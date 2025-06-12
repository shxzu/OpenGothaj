package xyz.cucumber.base.module.feat.visuals;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.NumberSettings;

@ModuleInfo(category=Category.VISUALS, description="Changes Item", name="Ambiance", priority=ArrayPriority.LOW)
public class AmbianceModule
extends Mod {
    private final NumberSettings time = new NumberSettings("Time", 0.0, 0.0, 22999.0, 1.0);
    private final NumberSettings speed = new NumberSettings("Time Speed", 0.0, 0.0, 20.0, 1.0);

    public AmbianceModule() {
        this.addSettings(this.time, this.speed);
    }

    @Override
    public void onEnable() {
        this.mc.theWorld.setRainStrength(0.0f);
        this.mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
        this.mc.theWorld.getWorldInfo().setRainTime(0);
        this.mc.theWorld.getWorldInfo().setThunderTime(0);
        this.mc.theWorld.getWorldInfo().setRaining(false);
        this.mc.theWorld.getWorldInfo().setThundering(false);
        super.onEnable();
    }

    @EventListener
    public void onRender3D(EventRender3D event) {
        this.mc.theWorld.setWorldTime((long)(this.time.getValue() + (double)System.currentTimeMillis() * this.speed.getValue()));
    }

    @EventListener
    public void onMotion(EventMotion event) {
        if (this.mc.thePlayer.ticksExisted % 20 == 0) {
            this.mc.theWorld.setRainStrength(0.0f);
            this.mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
            this.mc.theWorld.getWorldInfo().setRainTime(0);
            this.mc.theWorld.getWorldInfo().setThunderTime(0);
            this.mc.theWorld.getWorldInfo().setRaining(false);
            this.mc.theWorld.getWorldInfo().setThundering(false);
        }
    }

    @EventListener
    public void onPacket(EventReceivePacket event) {
        S2BPacketChangeGameState s2b;
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
        if (event.getPacket() instanceof S2BPacketChangeGameState && ((s2b = (S2BPacketChangeGameState)event.getPacket()).getGameState() == 1 || s2b.getGameState() == 2)) {
            event.setCancelled(true);
        }
    }
}
