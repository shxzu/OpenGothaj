package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAirCollide;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to not take fall damage", name="No Fall", key=0)
public class NoFallModule
extends Mod {
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Clutch", "Verus", "Vulcan", "Hypixel Timer", "Spoof", "Grim"});
    public Timer timer = new Timer();
    public boolean canWork;
    public boolean pickup;

    public NoFallModule() {
        this.addSettings(this.mode);
    }

    @EventListener(value=EventPriority.HIGHEST)
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onLook(EventLook e) {
        switch (this.mode.getMode().toLowerCase()) {
            case "clutch": {
                if (this.mc.thePlayer.fallDistance > 2.9f) {
                    int item = InventoryUtils.getBucketSlot();
                    if (item == -1) {
                        item = InventoryUtils.getCobwebSlot();
                    }
                    if (item == -1) {
                        if (!this.canWork || this.pickup) break;
                        RotationUtils.customRots = false;
                        RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                        RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                        this.canWork = false;
                        this.pickup = false;
                        return;
                    }
                    this.mc.thePlayer.inventory.currentItem = item;
                    RotationUtils.customRots = true;
                    RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                    RotationUtils.serverPitch = 90.0f;
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                    this.canWork = true;
                    if (this.mc.thePlayer.isInWater() || this.mc.thePlayer.isInWeb || this.pickup || this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.water || this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.air) break;
                    this.mc.rightClickMouse();
                    this.pickup = true;
                    this.timer.reset();
                    break;
                }
                if (!this.canWork) break;
                if (this.mc.thePlayer.isInWater() && this.pickup) {
                    this.mc.rightClickMouse();
                    this.pickup = false;
                } else {
                    RotationUtils.customRots = false;
                    RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                    RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                    this.canWork = false;
                    this.pickup = false;
                }
                if (!this.timer.hasTimeElapsed(150.0, false)) break;
                RotationUtils.customRots = false;
                RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                this.canWork = false;
                this.pickup = false;
            }
        }
    }

    @EventListener(value=EventPriority.HIGHEST)
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        if (e.getType() != EventType.PRE) {
            return;
        }
        this.setInfo(this.mode.getMode());
        switch (this.mode.getMode().toLowerCase()) {
            case "hypixel timer": {
                if ((double)this.mc.thePlayer.fallDistance - this.mc.thePlayer.motionY > 3.0) {
                    this.mc.timer.timerSpeed = 0.5f;
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                    this.mc.thePlayer.fallDistance = 0.0f;
                    break;
                }
                this.mc.timer.timerSpeed = 1.0f;
                break;
            }
            case "clutch": {
                if (!this.canWork) break;
                e.setYaw(RotationUtils.serverYaw);
                e.setPitch(RotationUtils.serverPitch);
                break;
            }
            case "verus": {
                if (!((double)this.mc.thePlayer.fallDistance > 2.9)) break;
                this.mc.thePlayer.motionX *= 0.6;
                this.mc.thePlayer.motionZ *= 0.6;
                this.mc.thePlayer.motionY = 0.0;
                e.setOnGround(true);
                this.mc.thePlayer.fallDistance = 0.0f;
                break;
            }
            case "vulcan": {
                if (!((double)this.mc.thePlayer.fallDistance > 2.9)) break;
                this.mc.thePlayer.motionY = -0.11;
                e.setOnGround(true);
                this.mc.thePlayer.fallDistance = 0.0f;
                break;
            }
            case "spoof": {
                if (!((double)this.mc.thePlayer.fallDistance > 2.9)) break;
                e.setOnGround(true);
                this.mc.thePlayer.fallDistance = 0.0f;
                break;
            }
            case "grim": {
                if (!(this.mc.thePlayer.fallDistance >= 3.0f)) break;
                this.mc.thePlayer.motionX *= 0.2;
                this.mc.thePlayer.motionZ *= 0.2;
                float distance = this.mc.thePlayer.fallDistance;
                if (MovementUtils.isOnGround(2.0)) {
                    if (distance > 2.0f) {
                        MovementUtils.strafe(0.19f);
                    }
                    if (distance > 3.0f && MovementUtils.getSpeed() < 0.2) {
                        e.setOnGround(true);
                        distance = 0.0f;
                    }
                }
                this.mc.thePlayer.fallDistance = distance;
            }
        }
    }

    @EventListener
    public void onReceivePacket(EventReceivePacket e) {
        switch (this.mode.getMode().toLowerCase()) {
            case "grim": {
                if (!(e.getPacket() instanceof S08PacketPlayerPosLook)) break;
                this.canWork = true;
            }
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onAirCollide(EventAirCollide e) {
        switch (this.mode.getMode().toLowerCase()) {
            case "grim": {
                if (!(this.mc.thePlayer.fallDistance >= 3.0f) || this.canWork || !(this.mc.theWorld.getBlockState(e.getPos()).getBlock() instanceof BlockAir) || this.mc.thePlayer.isSneaking()) break;
                double x = e.getPos().getX();
                double y = e.getPos().getY();
                double z = e.getPos().getZ();
                if (!(y < this.mc.thePlayer.posY)) break;
                e.setReturnValue(AxisAlignedBB.fromBounds(-15.0, -1.0, -15.0, 15.0, 1.0, 15.0).offset(x, y, z));
            }
        }
    }
}
