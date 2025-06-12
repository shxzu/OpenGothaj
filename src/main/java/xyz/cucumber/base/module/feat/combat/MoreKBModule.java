package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventMoveForward;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.combat.VelocityModule;
import xyz.cucumber.base.module.settings.ModeSettings;

@ModuleInfo(category=Category.COMBAT, description="Allows you to deal more knockback", name="More KB", key=0, priority=ArrayPriority.HIGH)
public class MoreKBModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Packet", "Packet legit", "WTap", "STap"});
    VelocityModule velocity;

    public MoreKBModule() {
        this.addSettings(this.mode);
    }

    @Override
    public void onEnable() {
        this.velocity = (VelocityModule)Client.INSTANCE.getModuleManager().getModule(VelocityModule.class);
    }

    @EventListener
    public void onTick(EventTick e) {
        this.setInfo(this.mode.getMode());
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onAttack(EventAttack e) {
        if (this.velocity.mode.getMode().equalsIgnoreCase("Polar")) {
            return;
        }
        KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
        switch (this.mode.getMode().toLowerCase()) {
            case "packet": {
                if (this.mc.thePlayer.isSprinting()) {
                    this.mc.thePlayer.setSprinting(false);
                }
                this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                this.mc.thePlayer.setSprinting(true);
                break;
            }
            case "packet legit": {
                if (!this.mc.gameSettings.keyBindForward.pressed || this.mc.thePlayer.isSneaking()) {
                    return;
                }
                if (!this.mc.thePlayer.isSprinting()) break;
                this.mc.thePlayer.setSprinting(false);
                this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                this.mc.thePlayer.serverSprintState = true;
                this.mc.thePlayer.setSprinting(true);
            }
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMoveForward(EventMoveForward e) {
        block8: {
            if (this.velocity.mode.getMode().equalsIgnoreCase("Polar")) {
                return;
            }
            KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
            if (ka.target == null) break block8;
            switch (this.mode.getMode().toLowerCase()) {
                case "wtap": {
                    if (ka.target.hurtTime != 10) break;
                    e.setReset(true);
                }
            }
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMoveButton(EventMoveButton e) {
        block9: {
            if (this.velocity.mode.getMode().equalsIgnoreCase("Polar")) {
                return;
            }
            KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
            if (ka.target == null) break block9;
            switch (this.mode.getMode().toLowerCase()) {
                case "stap": {
                    if (ka.target.hurtTime == 10) {
                        e.backward = true;
                        e.forward = false;
                    }
                    if (ka.target.hurtTime != 9) break;
                    e.backward = false;
                    e.forward = true;
                }
            }
        }
    }
}
