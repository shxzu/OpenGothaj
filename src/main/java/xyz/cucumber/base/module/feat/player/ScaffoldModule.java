package xyz.cucumber.base.module.feat.player;

import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventClick;
import xyz.cucumber.base.events.ext.EventGround;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventRotation;
import xyz.cucumber.base.events.ext.EventSafeWalk;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.events.ext.EventTimeDelay;
import xyz.cucumber.base.events.ext.EventUpdate;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.player.SmoothRotationModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.FastNoiseLite;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.PLAYER, description="Automatically builds for you", name="Scaffold", priority=ArrayPriority.HIGH)
public class ScaffoldModule
extends Mod {
    public int posY;
    public int spoofSlot;
    public int itemBefore;
    public int enabledTicks;
    public int rotationTicks;
    public int dragClickTicks;
    public int sneakTicks;
    public int timerTicks;
    public int placed;
    public int adStrafeBlocks;
    public int yadStrafeBlocks;
    public int intaveBypassBlocks;
    public int offGroundTicks;
    public boolean wasJump;
    public boolean adStrafeDirection;
    public boolean silentSneak;
    public float lastYaw;
    public float lastPitch;
    public float scaffoldYaw;
    public float scaffoldPitch;
    public float startYaw;
    public float polarTicks;
    FastNoiseLite noise = new FastNoiseLite();
    public BlockPos blockPos;
    public EnumFacing facing;
    public Timer adStrafeTimer = new Timer();
    public Timer jumpBlockPlacementTimer = new Timer();
    public Timer offGroundSpeedTimer = new Timer();
    public Timer intaveBypassTimer = new Timer();
    public ModeSettings rotations = new ModeSettings("Rotations", new String[]{"Static god", "Polar", "Intave", "Hypixel", "Keep", "Snap", "Telly", "Direct", "None"});
    public BooleanSettings polarFull = new BooleanSettings("Polar full", () -> this.rotations.getMode().equalsIgnoreCase("Polar"), false);
    public BooleanSettings polarStrong = new BooleanSettings("Polar strong", () -> this.rotations.getMode().equalsIgnoreCase("Polar"), false);
    public ModeSettings sprint = new ModeSettings("Sprint", new String[]{"Allways", "Off", "Legit", "Switch", "Motion modifier", "No packet", "Packet legit", "Old intave"});
    public ModeSettings motionModifierSprint = new ModeSettings("Motion modifier sprint", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier"), new String[]{"Air", "Ground", "Both", "Off"});
    public ModeSettings motionModifierSprintGround = new ModeSettings("Sprint ground mode", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierSprint.getMode().equalsIgnoreCase("Ground") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")), new String[]{"On place", "Tick", "On jump", "Place and jump", "Tick and jump", "Allways"});
    public ModeSettings motionModifierSprintAir = new ModeSettings("Sprint air mode", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierSprint.getMode().equalsIgnoreCase("Air") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")), new String[]{"On place", "Tick", "Allways"});
    public ModeSettings sprintPacket = new ModeSettings("Sprint packet", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && !this.motionModifierSprint.getMode().equalsIgnoreCase("Off"), new String[]{"Air", "Ground", "Both", "Off"});
    public ModeSettings motionModifierMode = new ModeSettings("Motion modifier mode", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier"), new String[]{"Air", "Ground", "Both"});
    public ModeSettings motionModifierGround = new ModeSettings("Ground mode", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierMode.getMode().equalsIgnoreCase("Ground") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")), new String[]{"On place", "Tick", "On jump", "Place and jump", "Tick and jump"});
    public ModeSettings motionModifierAir = new ModeSettings("Air mode", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierMode.getMode().equalsIgnoreCase("Air") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")), new String[]{"On place", "Tick"});
    public NumberSettings motionModifierTick = new NumberSettings("Tick delay", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierAir.getMode().equalsIgnoreCase("Tick") || this.motionModifierGround.getMode().equalsIgnoreCase("Tick") || this.motionModifierGround.getMode().equalsIgnoreCase("Tick and jump")), 3.0, 1.0, 40.0, 1.0);
    public NumberSettings groundMotion = new NumberSettings("Ground motion", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierMode.getMode().equalsIgnoreCase("Ground") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")), 1.0, 0.0, 3.0, 0.01);
    public NumberSettings airMotion = new NumberSettings("Air motion", () -> this.sprint.getMode().equalsIgnoreCase("Motion modifier") && (this.motionModifierMode.getMode().equalsIgnoreCase("Air") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")), 1.0, 0.0, 3.0, 0.01);
    public ModeSettings tower = new ModeSettings("Tower", new String[]{"None", "Vanilla", "NCP", "Hypixel", "Timer", "Intave"});
    public ModeSettings spoof = new ModeSettings("Spoof slot", new String[]{"None", "Normal", "Fake"});
    public BooleanSettings sprintWhenJump = new BooleanSettings("Sprint when jump", () -> !this.sprint.getMode().equalsIgnoreCase("Motion modifier"), true);
    public NumberSettings expand = new NumberSettings("Expand", 0.0, 0.0, 8.0, 1.0);
    public BooleanSettings timer = new BooleanSettings("Timer", false);
    public NumberSettings timerSpeed = new NumberSettings("Timer speed", () -> this.timer.isEnabled(), 1.0, 0.0, 2.0, 0.01);
    public NumberSettings timerDelay = new NumberSettings("Timer delay", () -> this.timer.isEnabled(), 4.0, 0.0, 20.0, 1.0);
    public NumberSettings timerTime = new NumberSettings("Timer time", () -> this.timer.isEnabled(), 2.0, 1.0, 40.0, 1.0);
    public ModeSettings sneak = new ModeSettings("Sneak", new String[]{"Off", "Normal", "Silent"});
    public NumberSettings sneakDelay = new NumberSettings("Sneak delay", () -> !this.sneak.getMode().equalsIgnoreCase("Off"), 4.0, 0.0, 20.0, 1.0);
    public NumberSettings sneakTime = new NumberSettings("Sneak time", () -> !this.sneak.getMode().equalsIgnoreCase("Off") && !this.sneak.getMode().equalsIgnoreCase("Silent"), 2.0, 1.0, 8.0, 1.0);
    public BooleanSettings safeWalk = new BooleanSettings("Safe walk", true);
    public BooleanSettings moveFix = new BooleanSettings("Move fix", true);
    public BooleanSettings swing = new BooleanSettings("Swing", false);
    public BooleanSettings serverSideSwing = new BooleanSettings("Server side swing", true);
    public BooleanSettings jump = new BooleanSettings("Jump", false);
    public BooleanSettings polarJump = new BooleanSettings("Polar jump", false);
    public BooleanSettings hypixelJump = new BooleanSettings("Hypixel jump", false);
    public BooleanSettings godbridgeJump = new BooleanSettings("Godbridge jump", false);
    public BooleanSettings intaveCloudBypass = new BooleanSettings("Intave cloud bypass", false);
    public BooleanSettings switchBack = new BooleanSettings("Switch back", true);
    public BooleanSettings adStrafe = new BooleanSettings("A D strafe", false);
    public NumberSettings adStrafeDelay = new NumberSettings("A D strafe delay", () -> this.adStrafe.isEnabled(), 0.0, 0.0, 10.0, 1.0);
    public BooleanSettings dragClick = new BooleanSettings("Drag click", false);

    public ScaffoldModule() {
        this.noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        this.addSettings(this.rotations, this.polarFull, this.polarStrong, this.sprint, this.motionModifierSprint, this.motionModifierSprintGround, this.motionModifierSprintAir, this.sprintPacket, this.motionModifierMode, this.motionModifierGround, this.motionModifierAir, this.motionModifierTick, this.groundMotion, this.airMotion, this.sprintWhenJump, this.tower, this.spoof, this.expand, this.moveFix, this.jump, this.polarJump, this.hypixelJump, this.godbridgeJump, this.intaveCloudBypass, this.adStrafe, this.adStrafeDelay, this.safeWalk, this.dragClick, this.swing, this.serverSideSwing, this.switchBack, this.timer, this.timerSpeed, this.timerDelay, this.timerTime, this.sneak, this.sneakDelay, this.sneakTime);
    }

    @Override
    public void onDisable() {
        SmoothRotationModule smoothRotation;
        this.mc.rightClickDelayTimer = 6;
        if (this.switchBack.isEnabled() && this.spoof.getMode().equalsIgnoreCase("None")) {
            this.mc.thePlayer.inventory.currentItem = this.itemBefore;
        }
        if (this.spoof.getMode().equalsIgnoreCase("Fake") && this.mc.thePlayer.inventory.currentItem != this.spoofSlot) {
            this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
        }
        if (!(smoothRotation = (SmoothRotationModule)Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class)).isEnabled() || !smoothRotation.sc.isEnabled()) {
            RotationUtils.customRots = false;
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
        }
        this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindSneak.getKeyCode());
        this.mc.timer.timerSpeed = 1.0f;
        this.enabledTicks = 11;
    }

    @Override
    public void onEnable() {
        if (this.sprint.getMode().equalsIgnoreCase("Legit")) {
            if (MovementUtils.isMoving() && this.mc.gameSettings.keyBindForward.pressed && (double)Math.abs(MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(RotationUtils.serverYaw)) < 66.5) {
                this.mc.thePlayer.setSprinting(true);
                this.mc.gameSettings.keyBindSprint.pressed = true;
            } else {
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
            }
        }
        this.placed = 0;
        this.mc.gameSettings.keyBindSneak.pressed = false;
        this.silentSneak = false;
        this.mc.rightClickDelayTimer = 0;
        this.enabledTicks = 0;
        this.sneakTicks = 0;
        this.timerTicks = 0;
        this.adStrafeBlocks = 0;
        this.intaveBypassBlocks = 0;
        this.startYaw = this.mc.thePlayer.rotationYaw;
        if (!RotationUtils.customRots) {
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
        }
        this.itemBefore = this.mc.thePlayer.inventory.currentItem;
        this.lastYaw = this.mc.thePlayer.rotationYaw - 180.0f;
        this.lastPitch = 79.0f;
        this.posY = (int)(this.mc.thePlayer.posY - 1.0);
        this.spoofSlot = this.mc.thePlayer.inventory.currentItem;
        this.scaffoldYaw = this.mc.thePlayer.rotationYaw - 180.0f;
        this.scaffoldPitch = 79.0f;
    }

    @Override
    public void onSettingChange() {
        if (this.jump.isEnabled()) {
            this.polarJump.setEnabled(false);
            this.hypixelJump.setEnabled(false);
            this.godbridgeJump.setEnabled(false);
        }
        if (this.polarJump.isEnabled()) {
            this.jump.setEnabled(false);
            this.hypixelJump.setEnabled(false);
            this.godbridgeJump.setEnabled(false);
        }
        if (this.hypixelJump.isEnabled()) {
            this.jump.setEnabled(false);
            this.polarJump.setEnabled(false);
            this.godbridgeJump.setEnabled(false);
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onSafeWalk(EventSafeWalk e) {
        if (this.safeWalk.isEnabled()) {
            e.setCancelled(true);
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMoveButton(EventMoveButton e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (this.sneak.getMode().equalsIgnoreCase("Normal")) {
            if (this.sneakDelay.getValue() == 0.0) {
                e.sneak = true;
            } else if ((double)this.sneakTicks >= this.sneakDelay.getValue()) {
                e.sneak = true;
                if ((double)this.sneakTicks >= this.sneakDelay.getValue() + this.sneakTime.getValue()) {
                    this.sneakTicks = 0;
                    e.sneak = false;
                }
            }
        }
        if (this.sneak.getMode().equalsIgnoreCase("Silent") && (double)this.sneakTicks >= this.sneakDelay.getValue()) {
            if (!this.silentSneak) {
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                this.silentSneak = true;
            } else {
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                this.silentSneak = false;
                this.sneakTicks = 0;
            }
        }
        if ((double)this.sneakTicks >= this.sneakDelay.getValue()) {
            ++this.sneakTicks;
        }
        if (this.adStrafe.isEnabled() && !MovementUtils.isGoingDiagonally()) {
            int delay = (int)(60.0 * Math.max(1.0, this.adStrafeDelay.getValue() / 2.0));
            if ((double)this.adStrafeBlocks >= this.adStrafeDelay.getValue() && this.adStrafeTimer.hasTimeElapsed(delay, true)) {
                this.adStrafeBlocks = 0;
                boolean bl = this.adStrafeDirection = !this.adStrafeDirection;
            }
            if (!this.adStrafeTimer.hasTimeElapsed(delay, false) && MovementUtils.isMoving() && !Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindLeft.getKeyCode()) && !Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindRight.getKeyCode())) {
                if (this.adStrafeDirection) {
                    e.left = true;
                } else {
                    e.right = true;
                }
            }
        }
    }

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (e.getPacket() instanceof C09PacketHeldItemChange && this.spoof.getMode().equalsIgnoreCase("Fake")) {
            e.setCancelled(true);
        }
        if (e.getPacket() instanceof C0APacketAnimation && !this.serverSideSwing.isEnabled()) {
            e.setCancelled(true);
        }
    }

    @EventListener
    public void onRotation(EventRotation e) {
        if (!this.hasBlocks()) {
            return;
        }
    }

    @EventListener
    public void onLook(EventLook e) {
        if (!this.hasBlocks()) {
            return;
        }
        this.processBlockData();
        this.rotations();
        if (RotationUtils.customRots) {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
        }
    }

    @EventListener
    public void onRenderRotation(EventRenderRotation e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (RotationUtils.customRots) {
            if (this.rotations.getMode().equalsIgnoreCase("Polar")) {
                e.setYaw((float)Math.toDegrees(MovementUtils.getDirectionKeybinds(this.mc.thePlayer.rotationYaw - 180.0f)));
                e.setPitch(79.0f);
                e.setYaw(RotationUtils.serverYaw);
                e.setPitch(RotationUtils.serverPitch);
            } else {
                e.setYaw(RotationUtils.serverYaw);
                e.setPitch(RotationUtils.serverPitch);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onGround(EventGround e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (e.getType() == EventType.PRE) {
            var2_2 = this.tower.getMode().toLowerCase();
            tmp = -1;
            switch (var2_2.hashCode()) {
                case 1381910549: {
                    if (!var2_2.equals("hypixel")) break;
                    tmp = 1;
                    break;
                }
            }
            ** switch (tmp)
        }
lbl13:
        // 4 sources

    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onJump(EventJump e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (this.tower.getMode().toLowerCase().equals("intave") && !MovementUtils.isMoving()) {
            e.setMotionY(0.41);
        }
        if (this.sprint.getMode().equalsIgnoreCase("Motion modifier")) {
            if ((this.motionModifierSprint.getMode().equalsIgnoreCase("Ground") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")) && (this.motionModifierSprintGround.getMode().equalsIgnoreCase("On jump") || this.motionModifierSprintGround.getMode().equalsIgnoreCase("Tick and jump") || this.motionModifierSprintGround.getMode().equalsIgnoreCase("Place and jump"))) {
                this.mc.gameSettings.keyBindSprint.pressed = true;
                this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Ground") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
            }
            if ((this.motionModifierMode.getMode().equalsIgnoreCase("Ground") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")) && (this.motionModifierGround.getMode().equalsIgnoreCase("On jump") || this.motionModifierGround.getMode().equalsIgnoreCase("Tick and jump") || this.motionModifierGround.getMode().equalsIgnoreCase("Place and jump"))) {
                this.mc.thePlayer.motionX *= this.groundMotion.getValue();
                this.mc.thePlayer.motionZ *= this.groundMotion.getValue();
            }
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onTimeDelay(EventTimeDelay e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (this.timer.isEnabled()) {
            if (this.timerDelay.getValue() == 0.0) {
                this.mc.timer.timerSpeed = (float)this.timerSpeed.getValue();
            } else if ((double)this.timerTicks >= this.timerDelay.getValue()) {
                this.mc.timer.timerSpeed = (float)this.timerSpeed.getValue();
                if ((double)this.timerTicks >= this.timerDelay.getValue() + this.timerTime.getValue()) {
                    this.timerTicks = 0;
                    this.mc.timer.timerSpeed = 1.0f;
                }
            }
            if ((double)this.timerTicks >= this.timerDelay.getValue()) {
                ++this.timerTicks;
            }
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onTick(EventTick e) {
        if (!this.hasBlocks()) {
            return;
        }
        this.rotationTicks = this.mc.thePlayer.ticksExisted + 1;
        this.polarTicks += 1.0f;
        ++this.enabledTicks;
        this.mc.thePlayer.jumpTicks = 0;
        if (this.mc.thePlayer.motionX != 0.0 && this.mc.thePlayer.motionZ != 0.0 && this.mc.thePlayer.onGround) {
            if (!Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindJump.getKeyCode())) {
                if (this.polarJump.isEnabled() || this.godbridgeJump.isEnabled()) {
                    if (this.placed >= 8) {
                        this.jump();
                    }
                } else if (this.jump.isEnabled() || this.hypixelJump.isEnabled()) {
                    this.jump();
                }
            }
            this.wasJump = true;
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onUpdate(EventUpdate e) {
        if (!this.hasBlocks()) {
            return;
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (this.intaveCloudBypass.isEnabled()) {
            if (this.intaveBypassBlocks >= 40) {
                if (MovementUtils.isOnGround(0.01)) {
                    if (!this.intaveBypassTimer.hasTimeElapsed(350.0, false)) {
                        e.setY(1.0E7);
                    } else {
                        this.intaveBypassBlocks = 0;
                    }
                } else {
                    this.intaveBypassTimer.reset();
                }
            } else {
                this.intaveBypassTimer.reset();
            }
        }
        this.sprint(e);
        if (e.getType() == EventType.PRE) {
            this.tower();
            EventMotion event = e;
            if (RotationUtils.customRots) {
                event.setYaw(RotationUtils.serverYaw);
                event.setPitch(RotationUtils.serverPitch);
            }
        }
    }

    @EventListener
    public void onClick(EventClick e) {
        if (!this.hasBlocks()) {
            return;
        }
        this.offGroundTicks = this.mc.thePlayer.onGround ? 0 : ++this.offGroundTicks;
        ++this.dragClickTicks;
        this.place();
        if (this.polarJump.isEnabled() && !this.mc.gameSettings.keyBindJump.pressed && !this.mc.thePlayer.onGround && this.mc.thePlayer.fallDistance > 0.0f) {
            this.jumpBlockPlacementTimer.hasTimeElapsed(250.0, false);
        }
        if (this.dragClick.isEnabled()) {
            this.fakeClick();
        }
    }

    @EventListener
    public void onMoveFlying(EventMoveFlying e) {
        if (!this.hasBlocks()) {
            return;
        }
        if (this.moveFix.isEnabled() && RotationUtils.customRots && !this.mc.isSingleplayer()) {
            e.setCancelled(true);
            MovementUtils.silentMoveFix(e);
        }
    }

    public void fakeClick() {
        if (this.rotations.getMode().equalsIgnoreCase("Static god") && (this.mc.gameSettings.keyBindSneak.pressed || !MovementUtils.isMoving())) {
            return;
        }
        if (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock && this.dragClickTicks >= 1 && Math.random() > 0.1 && this.blockPos != null && this.mc.objectMouseOver.getBlockPos() != null && this.mc.objectMouseOver.getBlockPos().equalsBlockPos(this.blockPos) && (this.mc.thePlayer.onGround || this.mc.objectMouseOver.sideHit != EnumFacing.UP)) {
            this.mc.rightClickMouse();
        }
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void tower() {
        switch (this.tower.getMode().toLowerCase()) {
            case "ncp": {
                if (this.mc.thePlayer.posY % 1.0 <= 0.41 && this.mc.gameSettings.keyBindJump.pressed) {
                    if (!MovementUtils.isMoving()) {
                        this.mc.thePlayer.motionX *= 0.0;
                        this.mc.thePlayer.motionZ *= 0.0;
                    }
                    if (this.mc.thePlayer.posY % 1.0 <= 0.41 && this.mc.gameSettings.keyBindJump.pressed) {
                        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, Math.floor(this.mc.thePlayer.posY), this.mc.thePlayer.posZ);
                        if (MovementUtils.getSpeed() > 0.05) {
                            this.mc.thePlayer.motionY -= 0.1;
                            MovementUtils.strafe((float)MovementUtils.getBaseMoveSpeed());
                        } else {
                            this.jump();
                        }
                    }
                }
                this.mc.thePlayer.jumpTicks = 0;
                break;
            }
            case "hypixel": {
                if (this.mc.thePlayer.posY % 1.0 <= 0.41 && this.mc.gameSettings.keyBindJump.pressed) {
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, Math.floor(this.mc.thePlayer.posY), this.mc.thePlayer.posZ);
                    this.mc.thePlayer.motionY -= 0.1;
                    MovementUtils.strafe((float)MovementUtils.getBaseMoveSpeed());
                }
                this.mc.thePlayer.jumpTicks = 0;
                break;
            }
            case "vanilla": {
                if (this.mc.thePlayer.posY % 1.0 <= 0.41 && this.mc.gameSettings.keyBindJump.pressed) {
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, Math.floor(this.mc.thePlayer.posY), this.mc.thePlayer.posZ);
                    this.mc.thePlayer.motionY -= 0.1;
                }
                this.mc.thePlayer.jumpTicks = 0;
                break;
            }
            case "timer": {
                if (!this.mc.gameSettings.keyBindJump.pressed) break;
                this.mc.timer.timerSpeed = 1.25f;
            }
        }
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void sprint(EventMotion e) {
        if (!this.sprintWhenJump.isEnabled() && Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindJump.getKeyCode())) {
            if (this.sprint.getMode().equalsIgnoreCase("Off")) {
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
            } else if (MovementUtils.isMoving() && this.mc.gameSettings.keyBindForward.pressed && (double)Math.abs(MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(RotationUtils.serverYaw)) < 66.5) {
                this.mc.thePlayer.setSprinting(true);
                this.mc.gameSettings.keyBindSprint.pressed = true;
            } else {
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
            }
            return;
        }
        switch (this.sprint.getMode().toLowerCase()) {
            case "legit": {
                if (MovementUtils.isMoving() && this.mc.gameSettings.keyBindForward.pressed && (double)Math.abs(MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(RotationUtils.serverYaw)) < 66.5) {
                    this.mc.thePlayer.setSprinting(true);
                    this.mc.gameSettings.keyBindSprint.pressed = true;
                    break;
                }
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
                break;
            }
            case "packet legit": {
                this.mc.gameSettings.keyBindSprint.pressed = true;
                if (!(this.mc.thePlayer.moveForward > 0.0f)) break;
                this.mc.thePlayer.setSprinting(true);
                break;
            }
            case "off": {
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
                break;
            }
            case "allways": {
                this.mc.gameSettings.keyBindSprint.pressed = true;
                if (!(this.mc.thePlayer.moveForward > 0.0f)) break;
                this.mc.thePlayer.setSprinting(true);
                break;
            }
            case "motion modifier": {
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
                if (this.mc.thePlayer.onGround) {
                    if (this.motionModifierSprint.getMode().equalsIgnoreCase("Ground") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")) {
                        if (this.motionModifierSprintGround.getMode().equalsIgnoreCase("Allways")) {
                            this.mc.gameSettings.keyBindSprint.pressed = true;
                            this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Ground") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
                        }
                        if ((this.motionModifierSprintGround.getMode().equalsIgnoreCase("Tick") || this.motionModifierSprintGround.getMode().equalsIgnoreCase("Tick and jump")) && (double)this.mc.thePlayer.ticksExisted % this.motionModifierTick.getValue() == 0.0) {
                            this.mc.gameSettings.keyBindSprint.pressed = true;
                            this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Ground") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
                        }
                    }
                    if (!this.motionModifierMode.getMode().equalsIgnoreCase("Ground") && !this.motionModifierMode.getMode().equalsIgnoreCase("Both") || !this.motionModifierGround.getMode().equalsIgnoreCase("Tick") && !this.motionModifierGround.getMode().equalsIgnoreCase("Tick and jump") || (double)this.mc.thePlayer.ticksExisted % this.motionModifierTick.getValue() != 0.0) break;
                    this.mc.thePlayer.motionX *= this.groundMotion.getValue();
                    this.mc.thePlayer.motionZ *= this.groundMotion.getValue();
                    break;
                }
                if (this.motionModifierSprint.getMode().equalsIgnoreCase("Air") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")) {
                    if (this.motionModifierSprintAir.getMode().equalsIgnoreCase("Allways")) {
                        this.mc.gameSettings.keyBindSprint.pressed = true;
                        this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Air") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
                    }
                    if (this.motionModifierSprintAir.getMode().equalsIgnoreCase("Tick") && (double)this.mc.thePlayer.ticksExisted % this.motionModifierTick.getValue() == 0.0) {
                        this.mc.gameSettings.keyBindSprint.pressed = true;
                        this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Air") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
                    }
                }
                if (!this.motionModifierMode.getMode().equalsIgnoreCase("Air") && !this.motionModifierMode.getMode().equalsIgnoreCase("Both") || !this.motionModifierAir.getMode().equalsIgnoreCase("Tick") || (double)this.mc.thePlayer.ticksExisted % this.motionModifierTick.getValue() != 0.0) break;
                this.mc.thePlayer.motionX *= this.airMotion.getValue();
                this.mc.thePlayer.motionZ *= this.airMotion.getValue();
                break;
            }
            case "old intave": {
                if (!this.mc.thePlayer.onGround && MovementUtils.isMoving() && MovementUtils.getSpeed() < 0.253 && this.mc.thePlayer.hurtTime == 0 && this.offGroundSpeedTimer.hasTimeElapsed(150.0, true)) {
                    MovementUtils.strafe(0.273f);
                }
                this.mc.gameSettings.keyBindSprint.pressed = false;
                this.mc.thePlayer.setSprinting(false);
                break;
            }
            case "no packet": {
                this.mc.gameSettings.keyBindSprint.pressed = true;
                break;
            }
            case "switch": {
                this.mc.gameSettings.keyBindSprint.pressed = this.mc.thePlayer.ticksExisted % 1 == 0;
                this.mc.thePlayer.setSprinting(this.mc.thePlayer.ticksExisted % 2 == 0);
                break;
            }
            case "switch no packet": {
                this.mc.gameSettings.keyBindSprint.pressed = this.mc.thePlayer.ticksExisted % 2 == 0;
                this.mc.thePlayer.setSprinting(false);
            }
        }
    }

    public void rotations() {
        boolean stop = false;
        float currentYaw = this.scaffoldYaw;
        if (MovementUtils.isMoving() && this.mc.thePlayer.hurtTime == 0) {
            currentYaw = (float)Math.toDegrees(MovementUtils.getDirectionKeybinds(this.mc.thePlayer.rotationYaw - 180.0f));
        }
        float toTurnYaw = currentYaw;
        float currentPitch = this.lastPitch;
        switch (this.rotations.getMode().toLowerCase()) {
            case "direct": {
                currentYaw += 180.0f;
                if (this.blockPos == null || this.facing == null) break;
                float pitch = 90.0f;
                while (pitch > 30.0f) {
                    if (RotationUtils.lookingAtBlock(this.blockPos, this.mc.thePlayer.rotationYaw, pitch, this.facing, false)) {
                        currentPitch = pitch;
                    }
                    pitch -= 1.0f;
                }
                break;
            }
            case "snap": {
                if (this.mc.theWorld.isAirBlock(new BlockPos(this.mc.thePlayer.posX, (double)this.posY, this.mc.thePlayer.posZ))) {
                    if (this.blockPos == null || this.facing == null) break;
                    float[] rots = RotationUtils.getDirectionToBlock(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ(), this.facing);
                    currentYaw = rots[0];
                    currentPitch = rots[1];
                    break;
                }
                currentYaw = this.mc.thePlayer.rotationYaw;
                currentPitch = this.mc.thePlayer.rotationPitch;
                break;
            }
            case "telly": {
                int ticks = 0;
                if (this.enabledTicks > 2) {
                    if ((this.mc.thePlayer.onGround || (double)this.mc.thePlayer.fallDistance >= 0.8 || this.offGroundTicks != 0 && this.offGroundTicks <= 2) && !this.mc.gameSettings.keyBindJump.pressed) {
                        stop = true;
                        if (!this.mc.thePlayer.onGround && this.mc.thePlayer.fallDistance == 0.0f && this.offGroundTicks > 1) {
                            if (this.rotationTicks == this.mc.thePlayer.ticksExisted) {
                                this.scaffoldYaw = RotationUtils.updateRotation(this.scaffoldYaw, this.mc.thePlayer.rotationYaw + 180.0f, 90.0f);
                                ++this.rotationTicks;
                            }
                        } else if (this.rotationTicks == this.mc.thePlayer.ticksExisted) {
                            this.scaffoldYaw = RotationUtils.updateRotation(this.scaffoldYaw, this.mc.thePlayer.rotationYaw, 90.0f);
                            ++this.rotationTicks;
                        }
                        if (this.blockPos != null && this.facing != null) {
                            this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, currentYaw, this.lastPitch, 84);
                        }
                    } else if (this.blockPos != null && this.facing != null) {
                        this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, currentYaw, this.lastPitch, 84);
                        MovingObjectPosition rotationRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                        if (!rotationRay.getBlockPos().equalsBlockPos(this.blockPos)) {
                            float[] rots = RotationUtils.getDirectionToBlock(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ(), this.facing);
                            int maxTicks = (int)Math.abs(MathHelper.wrapAngleTo180_float(this.scaffoldYaw - rots[0]) / 4.0f);
                            while (ticks <= maxTicks && !stop) {
                                this.scaffoldYaw = RotationUtils.updateRotation(this.scaffoldYaw, rots[0], 5.0f);
                                this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, this.scaffoldYaw, this.lastPitch, 84);
                                MovingObjectPosition stopRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                                if (stopRay.getBlockPos().equalsBlockPos(this.blockPos) && stopRay.sideHit == this.facing) {
                                    stop = true;
                                }
                                ++ticks;
                            }
                        }
                    }
                } else {
                    this.scaffoldYaw = this.mc.thePlayer.rotationYaw;
                }
                currentYaw = this.scaffoldYaw;
                currentPitch = this.scaffoldPitch;
                break;
            }
            case "keep": {
                if (this.mc.theWorld.isAirBlock(new BlockPos(this.mc.thePlayer.posX, (double)this.posY, this.mc.thePlayer.posZ)) && this.blockPos != null && this.facing != null) {
                    float[] rots = RotationUtils.getDirectionToBlock(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ(), this.facing);
                    this.scaffoldYaw = rots[0];
                    this.scaffoldPitch = rots[1];
                }
                currentYaw = this.scaffoldYaw;
                currentPitch = this.scaffoldPitch;
                break;
            }
            case "polar": {
                currentYaw = this.scaffoldYaw;
                int ticks = 0;
                boolean yawSpeed = true;
                boolean pitchSpeed = true;
                int yawMultiplier = 1;
                int pitchMultiplier = 4;
                float yawAdd = this.noise.GetNoise(this.polarTicks * (float)yawSpeed + 50.0f, this.polarTicks * (float)yawSpeed + 50.0f) * (float)yawMultiplier;
                float pitchAdd = this.noise.GetNoise(this.polarTicks * (float)pitchSpeed, this.polarTicks * (float)pitchSpeed) * (float)pitchMultiplier;
                this.scaffoldPitch = 78.0f;
                if (this.mc.thePlayer.motionX != 0.0 && this.mc.thePlayer.motionZ != 0.0) {
                    if (this.blockPos != null && this.facing != null) {
                        MovingObjectPosition checkRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                        if ((checkRay.getBlockPos().getX() != this.blockPos.getX() || checkRay.getBlockPos().getZ() != this.blockPos.getZ() || this.mc.gameSettings.keyBindJump.pressed && checkRay.getBlockPos().getY() != this.blockPos.getY() && this.polarFull.isEnabled()) && !stop) {
                            int maxTicks = 720;
                            while (ticks < maxTicks && !stop) {
                                MovingObjectPosition rotationRay;
                                if (yawMultiplier < 360) {
                                    ++yawMultiplier;
                                }
                                if (ticks >= 100 && this.mc.objectMouseOver.sideHit != EnumFacing.UP && this.polarStrong.isEnabled()) {
                                    pitchMultiplier = 6;
                                }
                                if ((rotationRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw + (yawAdd = this.noise.GetNoise(this.polarTicks * (float)yawSpeed + 50.0f, this.polarTicks * (float)yawSpeed + 50.0f) * (float)yawMultiplier), this.scaffoldPitch + (pitchAdd = this.noise.GetNoise(this.polarTicks * (float)pitchSpeed, this.polarTicks * (float)pitchSpeed) * (float)pitchMultiplier)}, this.mc.playerController.getBlockReachDistance(), 2.0)).getBlockPos().equalsBlockPos(this.blockPos) && rotationRay.sideHit == this.facing) {
                                    currentYaw = this.scaffoldYaw + yawAdd;
                                    currentPitch = this.scaffoldPitch + pitchAdd;
                                    ticks = maxTicks;
                                    stop = true;
                                }
                                this.polarTicks += 1.0f;
                                ++ticks;
                            }
                        } else {
                            int maxTicks = 100;
                            while (ticks < maxTicks) {
                                MovingObjectPosition rotationRay;
                                if (ticks >= 100 && this.mc.objectMouseOver.sideHit != EnumFacing.UP && this.polarStrong.isEnabled()) {
                                    pitchMultiplier = 6;
                                    maxTicks = 200;
                                }
                                if ((rotationRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw + (yawAdd = this.noise.GetNoise(this.polarTicks * (float)yawSpeed + 50.0f, this.polarTicks * (float)yawSpeed + 50.0f) * (float)yawMultiplier), this.scaffoldPitch + (pitchAdd = this.noise.GetNoise(this.polarTicks * (float)pitchSpeed, this.polarTicks * (float)pitchSpeed) * (float)pitchMultiplier)}, this.mc.playerController.getBlockReachDistance(), 2.0)).getBlockPos().equalsBlockPos(this.blockPos) && rotationRay.sideHit == this.facing) {
                                    currentYaw = this.scaffoldYaw + yawAdd;
                                    currentPitch = this.scaffoldPitch + pitchAdd;
                                    ticks = maxTicks;
                                }
                                this.polarTicks += 1.0f;
                                ++ticks;
                            }
                        }
                    } else {
                        yawAdd = this.noise.GetNoise(this.polarTicks * (float)yawSpeed + 50.0f, this.polarTicks * (float)yawSpeed + 50.0f) * (float)yawMultiplier;
                        pitchAdd = this.noise.GetNoise(this.polarTicks * (float)pitchSpeed, this.polarTicks * (float)pitchSpeed) * (float)pitchMultiplier;
                        currentYaw = this.scaffoldYaw + yawAdd;
                        currentPitch = this.scaffoldPitch + pitchAdd;
                    }
                } else {
                    currentYaw = this.scaffoldYaw + yawAdd;
                }
                currentPitch = MathHelper.clamp_float(currentPitch, -90.0f, 90.0f);
                break;
            }
            case "intave": {
                currentYaw = this.scaffoldYaw;
                int ticks = 0;
                if (this.blockPos != null && this.facing != null) {
                    MovingObjectPosition rotationRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                    this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, currentYaw, this.lastPitch, 84);
                    if (rotationRay != null && !rotationRay.getBlockPos().equalsBlockPos(this.blockPos)) {
                        float[] rots = RotationUtils.getDirectionToBlock(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ(), this.facing);
                        int maxTicks = (int)Math.abs(MathHelper.wrapAngleTo180_float(this.scaffoldYaw - rots[0]) / 4.0f);
                        while (ticks <= maxTicks && !stop) {
                            this.scaffoldYaw = RotationUtils.updateRotation(this.scaffoldYaw, rots[0], 5.0f);
                            this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, this.scaffoldYaw, this.lastPitch, 84);
                            MovingObjectPosition stopRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                            if (stopRay.getBlockPos().equalsBlockPos(this.blockPos) && stopRay.sideHit == this.facing) {
                                stop = true;
                            }
                            ++ticks;
                        }
                    }
                }
                currentYaw = this.scaffoldYaw;
                currentPitch = this.scaffoldPitch;
                break;
            }
            case "hypixel": {
                currentYaw = this.scaffoldYaw;
                int ticks = 0;
                if (this.blockPos != null && this.facing != null) {
                    MovingObjectPosition rotationRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                    this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, currentYaw, this.lastPitch, 90);
                    if (!rotationRay.getBlockPos().equalsBlockPos(this.blockPos)) {
                        float[] rots = RotationUtils.getDirectionToBlock(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ(), this.facing);
                        int maxTicks = (int)Math.abs(MathHelper.wrapAngleTo180_float(this.scaffoldYaw - rots[0]) / 4.0f);
                        while (ticks <= maxTicks && !stop) {
                            this.scaffoldYaw = RotationUtils.updateRotation(this.scaffoldYaw, rots[0], 5.0f);
                            this.scaffoldPitch = RotationUtils.getYawBasedPitch(this.blockPos, this.facing, this.scaffoldYaw, this.lastPitch, 90);
                            MovingObjectPosition stopRay = RotationUtils.rayCast(1.0f, new float[]{this.scaffoldYaw, this.scaffoldPitch}, this.mc.playerController.getBlockReachDistance(), 2.0);
                            if (stopRay.getBlockPos().equalsBlockPos(this.blockPos) && stopRay.sideHit == this.facing) {
                                stop = true;
                            }
                            ++ticks;
                        }
                    }
                }
                currentYaw = this.scaffoldYaw;
                currentPitch = this.scaffoldPitch;
                break;
            }
            case "static god": {
                this.scaffoldPitch = 75.7f;
                currentYaw = MathHelper.roundUp((int)(this.startYaw + 180.0f), 45);
                currentPitch = this.scaffoldPitch;
            }
        }
        if (!stop && !this.rotations.getMode().equalsIgnoreCase("Keep") && this.rotationTicks == this.mc.thePlayer.ticksExisted) {
            this.scaffoldYaw = RotationUtils.updateRotation(this.scaffoldYaw, toTurnYaw, this.rotations.getMode().equalsIgnoreCase("Polar") ? 10 : 20);
            ++this.rotationTicks;
        }
        float[] gcdRots = RotationUtils.getFixedRotation(new float[]{currentYaw, currentPitch}, new float[]{this.lastYaw, this.lastPitch});
        if (!Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class).isEnabled() || this.enabledTicks > 10) {
            RotationUtils.serverYaw = gcdRots[0];
            RotationUtils.serverPitch = gcdRots[1];
            RotationUtils.customRots = true;
        }
        this.lastYaw = currentYaw;
        this.lastPitch = currentPitch;
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void place() {
        boolean legit;
        if (this.rotations.getMode().equalsIgnoreCase("Static god") && this.mc.gameSettings.keyBindSneak.pressed) {
            this.sneakTicks = 0;
            this.timerTicks = 0;
            return;
        }
        BlockPos bp = this.mc.objectMouseOver.getBlockPos();
        EnumFacing ef = this.mc.objectMouseOver.sideHit;
        Vec3 hv = this.mc.objectMouseOver.hitVec;
        boolean bl = legit = this.expand.getValue() == 0.0 && !this.rotations.getMode().equalsIgnoreCase("Direct");
        if (!legit) {
            bp = this.blockPos;
            ef = this.facing;
            hv = RotationUtils.getVec3(bp, ef);
        }
        if (!this.mc.playerController.getIsHittingBlock() || !legit) {
            this.mc.rightClickDelayTimer = 4;
            if (!(this.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK && legit || this.mc.theWorld.getBlockState(bp).getBlock().getMaterial() == Material.air && legit)) {
                int i;
                int item = InventoryUtils.getBlockSlot(false);
                if (item == -1) {
                    RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                    RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                    RotationUtils.customRots = false;
                    return;
                }
                if (this.blockPos == null || this.facing == null) {
                    return;
                }
                if (this.mc.objectMouseOver == null) {
                    return;
                }
                if (!(this.rotations.getMode().equalsIgnoreCase("Direct") || this.rotations.getMode().equalsIgnoreCase("None") || this.expand.getValue() != 0.0 || this.blockPos.equalsBlockPos(bp) && this.facing == ef)) {
                    return;
                }
                ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(item);
                if (this.spoof.getMode().equalsIgnoreCase("Fake")) {
                    if (item != this.spoofSlot) {
                        this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(item));
                        this.spoofSlot = item;
                    }
                } else {
                    this.spoofSlot = this.mc.thePlayer.inventory.currentItem;
                    this.mc.thePlayer.inventory.currentItem = item;
                }
                if (this.sprint.getMode().equalsIgnoreCase("Packet legit") && this.mc.thePlayer.isSprinting()) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, stack, bp, ef, hv)) {
                    this.dragClickTicks = 0;
                    if (this.swing.isEnabled()) {
                        this.mc.thePlayer.swingItem();
                    } else {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                    if (this.polarJump.isEnabled() || this.godbridgeJump.isEnabled()) {
                        if (this.placed >= 8) {
                            this.placed = 0;
                        }
                        this.placed = !MovementUtils.isGoingDiagonally() && this.mc.thePlayer.motionX != 0.0 && this.mc.thePlayer.motionZ != 0.0 && this.mc.thePlayer.onGround ? ++this.placed : 0;
                    } else {
                        ++this.placed;
                    }
                    ++this.adStrafeBlocks;
                    ++this.intaveBypassBlocks;
                    ++this.sneakTicks;
                    ++this.timerTicks;
                    if (this.sprint.getMode().equalsIgnoreCase("Motion modifier")) {
                        if (this.mc.thePlayer.onGround) {
                            if ((this.motionModifierSprint.getMode().equalsIgnoreCase("Ground") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")) && (this.motionModifierSprintGround.getMode().equalsIgnoreCase("On place") || this.motionModifierSprintGround.getMode().equalsIgnoreCase("Place and jump"))) {
                                this.mc.gameSettings.keyBindSprint.pressed = true;
                                this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Ground") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
                            }
                            if ((this.motionModifierMode.getMode().equalsIgnoreCase("Ground") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")) && (this.motionModifierGround.getMode().equalsIgnoreCase("On place") || this.motionModifierGround.getMode().equalsIgnoreCase("Place and jump"))) {
                                this.mc.thePlayer.motionX *= this.groundMotion.getValue();
                                this.mc.thePlayer.motionZ *= this.groundMotion.getValue();
                            }
                        } else {
                            if ((this.motionModifierSprint.getMode().equalsIgnoreCase("Air") || this.motionModifierSprint.getMode().equalsIgnoreCase("Both")) && this.motionModifierSprintAir.getMode().equalsIgnoreCase("On place")) {
                                this.mc.gameSettings.keyBindSprint.pressed = true;
                                this.mc.thePlayer.setSprinting((this.sprintPacket.getMode().equalsIgnoreCase("Air") || this.sprintPacket.getMode().equalsIgnoreCase("Both")) && this.mc.gameSettings.keyBindForward.pressed);
                            }
                            if ((this.motionModifierMode.getMode().equalsIgnoreCase("Air") || this.motionModifierMode.getMode().equalsIgnoreCase("Both")) && this.motionModifierAir.getMode().equalsIgnoreCase("On place")) {
                                this.mc.thePlayer.motionX *= this.airMotion.getValue();
                                this.mc.thePlayer.motionZ *= this.airMotion.getValue();
                            }
                        }
                    }
                }
                if (this.sprint.getMode().equalsIgnoreCase("Packet legit") && this.mc.thePlayer.isSprinting()) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                if (this.mc.thePlayer.getHeldItem() == null) {
                    return;
                }
                int n = i = stack != null ? stack.stackSize : 0;
                if (stack.stackSize == 0) {
                    this.mc.thePlayer.inventory.mainInventory[this.mc.thePlayer.inventory.currentItem] = null;
                } else if (stack.stackSize != i || this.mc.playerController.isInCreativeMode()) {
                    this.mc.entityRenderer.itemRenderer.resetEquippedProgress();
                }
                if (this.spoof.getMode().equalsIgnoreCase("Normal")) {
                    this.mc.thePlayer.inventory.currentItem = this.spoofSlot;
                }
            }
        }
    }

    public void processBlockData() {
        if (this.jump.isEnabled() || this.polarJump.isEnabled() || this.hypixelJump.isEnabled()) {
            if (this.mc.gameSettings.keyBindJump.pressed) {
                this.posY = (int)(this.mc.thePlayer.posY - 1.0);
            }
        } else {
            this.posY = (int)(this.mc.thePlayer.posY - 1.0);
        }
        int currentY = this.posY;
        if (this.mc.thePlayer.onGround) {
            double cfr_ignored_0 = this.mc.thePlayer.posY;
        }
        if ((this.hypixelJump.isEnabled() || this.polarJump.isEnabled()) && !this.mc.gameSettings.keyBindJump.pressed && !this.mc.thePlayer.onGround && ((double)this.mc.thePlayer.fallDistance > 0.0 && (double)this.mc.thePlayer.fallDistance < 0.08 && (int)this.mc.thePlayer.posY <= this.posY + 2 || (double)this.mc.thePlayer.fallDistance > 0.95)) {
            currentY = this.posY + 1;
        }
        if (this.expand.getValue() == 0.0) {
            this.blockPos = this.getBlockPos(this.mc.thePlayer.posX, currentY, this.mc.thePlayer.posZ);
        } else {
            Vec3 vec = this.expand(new Vec3(this.mc.thePlayer.posX, currentY, this.mc.thePlayer.posZ));
            this.setBlockFacingOld(new BlockPos(vec.xCoord, vec.yCoord + 1.0, vec.zCoord));
        }
        if (this.blockPos != null && this.expand.getValue() == 0.0) {
            this.facing = this.getPlaceSide(this.mc.thePlayer.posX, currentY, this.mc.thePlayer.posZ);
        }
    }

    public static boolean isPosSolid(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }

    private Vec3 expand(Vec3 position) {
        if (this.expand.getValue() > 0.0) {
            double direction = MovementUtils.getDirection(this.mc.thePlayer.rotationYaw);
            Vec3 expandVector = new Vec3(-Math.sin(direction), 0.0, Math.cos(direction));
            int bestExpand = 0;
            int i = 0;
            while ((double)i < this.expand.getValue()) {
                if (!MovementUtils.isMoving()) break;
                Vec3 vec = position.addVector(0.0, -1.0, 0.0).add(expandVector.multiply(i));
                this.setBlockFacingOld(new BlockPos(vec.xCoord, (double)this.posY, vec.zCoord));
                if (this.blockPos != null && this.facing != EnumFacing.UP) {
                    bestExpand = i;
                }
                ++i;
            }
            position = position.add(expandVector.multiply(bestExpand));
            position.yCoord = this.posY - 1;
        }
        return position;
    }

    public void setBlockFacingOld(BlockPos pos) {
        if (this.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(0, -1, 0);
            this.facing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(-1, 0, 0);
            this.facing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(1, 0, 0);
            this.facing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(0, 0, -1);
            this.facing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(0, 0, 1);
            this.facing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(pos.add(-1, 0, -1)).getBlock() != Blocks.air) {
            this.facing = EnumFacing.EAST;
            this.blockPos = pos.add(-1, 0, -1);
        } else if (this.mc.theWorld.getBlockState(pos.add(1, 0, 1)).getBlock() != Blocks.air) {
            this.facing = EnumFacing.WEST;
            this.blockPos = pos.add(1, 0, 1);
        } else if (this.mc.theWorld.getBlockState(pos.add(1, 0, -1)).getBlock() != Blocks.air) {
            this.facing = EnumFacing.SOUTH;
            this.blockPos = pos.add(1, 0, -1);
        } else if (this.mc.theWorld.getBlockState(pos.add(-1, 0, 1)).getBlock() != Blocks.air) {
            this.facing = EnumFacing.NORTH;
            this.blockPos = pos.add(-1, 0, 1);
        } else if (this.mc.theWorld.getBlockState(pos.add(0, -1, 1)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(0, -1, 1);
            this.facing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(pos.add(0, -1, -1)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(0, -1, -1);
            this.facing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(pos.add(1, -1, 0)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(1, -1, 0);
            this.facing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(pos.add(-1, -1, 0)).getBlock() != Blocks.air) {
            this.blockPos = pos.add(-1, -1, 0);
            this.facing = EnumFacing.UP;
        }
    }

    private BlockPos getBlockPos(double posX, double posY, double posZ) {
        BlockPos playerPos = new BlockPos(posX, posY, posZ);
        ArrayList<Vec3> positions = new ArrayList<Vec3>();
        HashMap<Vec3, BlockPos> hashMap = new HashMap<Vec3, BlockPos>();
        int y = playerPos.getY() - 1;
        while (y <= playerPos.getY()) {
            int x = playerPos.getX() - 5;
            while (x <= playerPos.getX() + 5) {
                int z = playerPos.getZ() - 5;
                while (z <= playerPos.getZ() + 5) {
                    if (ScaffoldModule.isValidBock(new BlockPos(x, y, z))) {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
                        double ex = MathHelper.clamp_double(posX, blockPos.getX(), (double)blockPos.getX() + block.getBlockBoundsMaxX());
                        double ey = MathHelper.clamp_double(posY + 1.0, blockPos.getY(), (double)blockPos.getY() + block.getBlockBoundsMaxY());
                        double ez = MathHelper.clamp_double(posZ, blockPos.getZ(), (double)blockPos.getZ() + block.getBlockBoundsMaxZ());
                        Vec3 vec3 = new Vec3(ex, ey, ez);
                        positions.add(vec3);
                        hashMap.put(vec3, blockPos);
                    }
                    ++z;
                }
                ++x;
            }
            ++y;
        }
        if (positions.isEmpty()) {
            return null;
        }
        positions.sort(Comparator.comparingDouble(this::getBestBlock));
        return (BlockPos)hashMap.get(positions.get(0));
    }

    private EnumFacing getPlaceSide(double posX, double posY, double posZ) {
        Vec3 vec4;
        BlockPos bp;
        ArrayList<Vec3> positions = new ArrayList<Vec3>();
        HashMap<Vec3, EnumFacing> hashMap = new HashMap<Vec3, EnumFacing>();
        BlockPos playerPos = new BlockPos(posX, posY + 1.0, posZ);
        if (!(ScaffoldModule.isPosSolid(this.blockPos.add(0, 1, 0)) || this.blockPos.add(0, 1, 0).equals(playerPos) || this.mc.thePlayer.onGround)) {
            BlockPos bp2;
            BlockPos pos = new BlockPos(posX, posY, posZ);
            if (this.jump.isEnabled() || this.polarJump.isEnabled() || this.hypixelJump.isEnabled()) {
                if (this.mc.gameSettings.keyBindJump.pressed || (this.hypixelJump.isEnabled() || this.polarJump.isEnabled()) && !this.mc.thePlayer.onGround && (double)this.mc.thePlayer.fallDistance > 0.0 && (double)this.mc.thePlayer.fallDistance < 0.08 && (double)((int)this.mc.thePlayer.posY) <= posY + 1.0 || (double)this.mc.thePlayer.fallDistance > 0.95) {
                    bp2 = this.blockPos.add(0, 1, 0);
                    Vec3 vec42 = this.getBestHitFeet(bp2);
                    positions.add(vec42);
                    hashMap.put(vec42, EnumFacing.UP);
                }
            } else {
                bp2 = this.blockPos.add(0, 1, 0);
                Vec3 vec43 = this.getBestHitFeet(bp2);
                positions.add(vec43);
                hashMap.put(vec43, EnumFacing.UP);
            }
        }
        if (!ScaffoldModule.isPosSolid(this.blockPos.add(1, 0, 0)) && !this.blockPos.add(1, 0, 0).equals(playerPos)) {
            bp = this.blockPos.add(1, 0, 0);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.EAST);
        }
        if (!ScaffoldModule.isPosSolid(this.blockPos.add(-1, 0, 0)) && !this.blockPos.add(-1, 0, 0).equals(playerPos)) {
            bp = this.blockPos.add(-1, 0, 0);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.WEST);
        }
        if (!ScaffoldModule.isPosSolid(this.blockPos.add(0, 0, 1)) && !this.blockPos.add(0, 0, 1).equals(playerPos)) {
            bp = this.blockPos.add(0, 0, 1);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.SOUTH);
        }
        if (!ScaffoldModule.isPosSolid(this.blockPos.add(0, 0, -1)) && !this.blockPos.add(0, 0, -1).equals(playerPos)) {
            bp = this.blockPos.add(0, 0, -1);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.NORTH);
        }
        positions.sort(Comparator.comparingDouble(vec3 -> this.mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord)));
        if (!positions.isEmpty()) {
            Vec3 vec5 = this.getBestHitFeet(this.blockPos);
            if (this.mc.thePlayer.getDistance(vec5.xCoord, vec5.yCoord, vec5.zCoord) >= this.mc.thePlayer.getDistance(((Vec3)positions.get((int)0)).xCoord, ((Vec3)positions.get((int)0)).yCoord, ((Vec3)positions.get((int)0)).zCoord)) {
                return (EnumFacing)hashMap.get(positions.get(0));
            }
        }
        return null;
    }

    private Vec3 getBestHitFeet(BlockPos blockPos) {
        Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
        double ex = MathHelper.clamp_double(this.mc.thePlayer.posX, blockPos.getX(), (double)blockPos.getX() + block.getBlockBoundsMaxX());
        double ey = MathHelper.clamp_double(this.mc.thePlayer.posY, blockPos.getY(), (double)blockPos.getY() + block.getBlockBoundsMaxY());
        double ez = MathHelper.clamp_double(this.mc.thePlayer.posZ, blockPos.getZ(), (double)blockPos.getZ() + block.getBlockBoundsMaxZ());
        return new Vec3(ex, ey, ez);
    }

    private double getBestBlock(Vec3 vec3) {
        return this.mc.thePlayer.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public static boolean isValidBock(BlockPos blockPos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
    }

    public boolean hasBlocks() {
        int item = InventoryUtils.getBlockSlot(this.hypixelJump.isEnabled());
        if (item == -1) {
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
            RotationUtils.customRots = false;
            return false;
        }
        return true;
    }

    public void jump() {
        double cfr_ignored_0 = this.mc.thePlayer.posY;
        EventJump event = new EventJump(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.getJumpUpwardsMotion());
        Client.INSTANCE.getEventBus().call(event);
        if (event.isCancelled()) {
            return;
        }
        float finalYaw = this.mc.thePlayer instanceof EntityPlayerSP ? event.getYaw() : this.mc.thePlayer.rotationYaw;
        this.mc.thePlayer.motionY = event.getMotionY();
        if (this.mc.thePlayer.isPotionActive(Potion.jump)) {
            this.mc.thePlayer.motionY += (double)((float)(this.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
        }
        if (this.sprint.getMode().equalsIgnoreCase("No packet") || this.mc.thePlayer.isSprinting()) {
            float f = finalYaw * ((float)Math.PI / 180);
            this.mc.thePlayer.motionX -= (double)(MathHelper.sin(f) * 0.2f);
            this.mc.thePlayer.motionZ += (double)(MathHelper.cos(f) * 0.2f);
        }
        this.mc.thePlayer.isAirBorne = true;
    }

    private class BlockData {
        public BlockPos position;
        public BlockPos targetPos;
        public EnumFacing face;

        private BlockData() {
        }
    }
}
