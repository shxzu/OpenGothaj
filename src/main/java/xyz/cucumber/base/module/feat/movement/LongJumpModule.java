package xyz.cucumber.base.module.feat.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you jump further", name="Long Jump", key=0)
public class LongJumpModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Intave Boat", "Intave", "Polar"});
    public int jumps = 0;

    public LongJumpModule() {
        this.addSettings(this.mode);
    }

    @Override
    public void onEnable() {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        this.jumps = 0;
    }

    @EventListener
    public void onMotion(EventMotion e) {
        if (e.getType() != EventType.PRE) {
            return;
        }
        this.setInfo(String.valueOf(this.mode.getMode()));
        switch (this.mode.getMode().toLowerCase()) {
            case "polar": {
                if (!this.mc.thePlayer.onGround) break;
                this.mc.thePlayer.jump();
                this.mc.thePlayer.jump();
                this.toggle();
                break;
            }
            case "intave boat": {
                if (!this.mc.thePlayer.isRiding()) break;
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX - Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw)) * 3.0, this.mc.thePlayer.posY + 2.0, this.mc.thePlayer.posZ + Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw)) * 3.0, false));
                this.toggle();
                break;
            }
            case "intave": {
                if (!this.mc.thePlayer.onGround) break;
                this.mc.thePlayer.jump();
                this.mc.thePlayer.jump();
                this.toggle();
            }
        }
    }
}
