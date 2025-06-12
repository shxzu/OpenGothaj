package xyz.cucumber.base.module.feat.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;

@ModuleInfo(category=Category.PLAYER, description="Regenerates your health faster then normal", name="Regen", key=0, priority=ArrayPriority.HIGH)
public class RegenModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Grim 1.17"});
    public NumberSettings health = new NumberSettings("Health", 19.0, 2.0, 19.0, 1.0);
    public NumberSettings grimPackets = new NumberSettings("Grim packets", () -> this.mode.getMode().equalsIgnoreCase("Grim 1.17"), 10.0, 1.0, 50.0, 1.0);

    public RegenModule() {
        this.addSettings(this.mode, this.health, this.grimPackets);
    }

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        if ((double)this.mc.thePlayer.getHealth() > this.health.getValue() || this.mc.thePlayer.fallDistance > 3.0f) {
            return;
        }
        switch (this.mode.getMode().toLowerCase()) {
            case "grim 1.17": {
                if (!(e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook)) break;
                C03PacketPlayer.C06PacketPlayerPosLook packet = (C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket();
                int i = 0;
                while ((double)i < this.grimPackets.getValue()) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
                    ++i;
                }
                break;
            }
        }
    }
}
