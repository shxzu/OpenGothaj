package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.settings.ModeSettings;

@ModuleInfo(category=Category.COMBAT, description="Allows you to deal criticals every hit", name="Criticals", key=0)
public class CriticalsModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Edit", "Ground", "Packet", "Verus", "Vulcan", "NCP"});
    public int ticks;
    public int attacks;
    public boolean attacked;

    public CriticalsModule() {
        this.addSettings(this.mode);
    }

    @Override
    public void onEnable() {
        this.setInfo(this.mode.getMode());
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onAttack(EventAttack event) {
        if (event.getEntity() != null && event.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)event.getEntity();
            this.attacked = true;
            if (ent.hurtTime <= 2 && this.mode.getMode().equalsIgnoreCase("Packet")) {
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1, this.mc.thePlayer.posZ, false));
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.01, this.mc.thePlayer.posZ, false));
            }
            ++this.attacks;
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion event) {
        if (event.getType() != EventType.PRE) {
            return;
        }
        KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
        if (!ka.isEnabled() || ka.target == null) {
            return;
        }
        switch (this.mode.getMode().toLowerCase()) {
            case "packet": {
                event.setOnGround(false);
                if (this.mc.thePlayer.ticksExisted % 20 != 0) break;
                event.setOnGround(true);
                break;
            }
            case "edit": {
                if (this.mc.thePlayer.onGround && this.attacked) {
                    ++this.ticks;
                    switch (this.ticks) {
                        case 1: {
                            event.setY(event.getY() + 5.0E-4);
                            break;
                        }
                        case 2: {
                            event.setY(event.getY() + 1.0E-4);
                            this.attacked = false;
                        }
                    }
                    event.setOnGround(false);
                    if (this.mc.thePlayer.ticksExisted % 20 != 0) break;
                    event.setOnGround(true);
                    break;
                }
                this.attacked = false;
                this.ticks = 0;
                break;
            }
            case "ground": {
                if (this.mc.thePlayer.onGround && this.attacked) {
                    ++this.ticks;
                    switch (this.ticks) {
                        case 1: {
                            event.setY(event.getY() + 5.0E-4);
                            break;
                        }
                        case 2: {
                            event.setY(event.getY() + 1.0E-4);
                            this.attacked = false;
                        }
                    }
                    event.setOnGround(false);
                    break;
                }
                this.attacked = false;
                this.ticks = 0;
                break;
            }
            case "verus": {
                if (this.attacked) {
                    ++this.ticks;
                    switch (this.ticks) {
                        case 1: {
                            event.setY(event.getY() + 0.001);
                            event.setOnGround(true);
                            break;
                        }
                        case 2: {
                            event.setOnGround(false);
                            this.attacked = false;
                        }
                    }
                    break;
                }
                this.attacked = false;
                this.ticks = 0;
                break;
            }
            case "vulcan": {
                if (this.attacked) {
                    ++this.ticks;
                    switch (this.ticks) {
                        case 1: {
                            event.setY(event.getY() + 0.164);
                            event.setOnGround(false);
                            break;
                        }
                        case 2: {
                            event.setY(event.getY() + 0.083);
                            event.setOnGround(false);
                            break;
                        }
                        case 3: {
                            event.setY(event.getY() + 0.003);
                            event.setOnGround(false);
                            this.attacked = false;
                        }
                    }
                    break;
                }
                this.attacked = false;
                this.ticks = 0;
                break;
            }
            case "ncp": {
                if (this.attacked) {
                    ++this.ticks;
                    switch (this.ticks) {
                        case 1: {
                            event.setY(event.getY() + 0.001);
                            event.setOnGround(true);
                            break;
                        }
                        case 2: {
                            event.setOnGround(false);
                            this.attacked = false;
                        }
                    }
                    break;
                }
                this.attacked = false;
                this.ticks = 0;
            }
        }
    }
}
