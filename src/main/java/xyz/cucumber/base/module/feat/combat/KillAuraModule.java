package xyz.cucumber.base.module.feat.combat;

import de.florianmichael.viamcp.fixes.AttackOrder;
import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.RandomUtils;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventClick;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.events.ext.EventTimeDelay;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.AnnoyerModule;
import xyz.cucumber.base.module.feat.combat.AutoRodModule;
import xyz.cucumber.base.module.feat.movement.NoFallModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.AntiFireModule;
import xyz.cucumber.base.module.feat.player.AutoArmorModule;
import xyz.cucumber.base.module.feat.player.AutoHealModule;
import xyz.cucumber.base.module.feat.player.InvManagerModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.feat.player.SmoothRotationModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.FastNoiseLite;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.COMBAT, description="Automatically attack targets around you", name="Kill Aura", key=19, priority=ArrayPriority.HIGH)
public class KillAuraModule
extends Mod {
    FastNoiseLite noise = new FastNoiseLite();
    public EntityLivingBase target;
    public float fakePolarYaw;
    public float fakePolarPitch;
    public int attackTimes;
    public double yawSpeed;
    public double pitchSpeed;
    public double randomCPS;
    public Timer clickTimer = new Timer();
    public Timer cpsRandomizationTimer = new Timer();
    public Timer rotationRandomizationTimer = new Timer();
    public Timer polarRotationTimer = new Timer();
    public int intaveBlockTicks;
    public int attackTick;
    public int polar2Ticks;
    public boolean allowedToWork = false;
    public boolean blockingStatus = false;
    public boolean canSnapRotation;
    public boolean wasMaxTurn;
    private static final List<Packet<?>> packetList = new ArrayList();
    private static final Set<Class<?>> NON_CANCELABLE_PACKETS = new HashSet<Class>(Arrays.asList(C01PacketChatMessage.class, C14PacketTabComplete.class, C01PacketEncryptionResponse.class, C01PacketPing.class, C00PacketLoginStart.class, C00PacketServerQuery.class, C00Handshake.class, C00PacketKeepAlive.class));
    private boolean isBlinking = false;
    private boolean isBlocking = false;
    private int autoBlockTick = 0;
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Custom", "Hypixel"});
    public ModeSettings Targets = new ModeSettings("Targets", new String[]{"Everything", "Players"});
    public ModeSettings sort = new ModeSettings("Sort", new String[]{"Distance", "health", "Smart", "Strongest player"});
    public ModeSettings autoBlock = new ModeSettings("Auto Block", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), new String[]{"Vanilla", "Legit", "Fake", "None"});
    public ModeSettings rotationMode = new ModeSettings("Rotations", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), new String[]{"Normal", "Polar noise", "Polar sin", "Polar snap", "Polar snap legit", "Snap", "None"});
    NumberSettings polarMinSpeed = new NumberSettings("Polar min speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 0.0, 5.0, 180.0, 1.0);
    NumberSettings polarMaxSpeed = new NumberSettings("Polar max speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 180.0, 5.0, 180.0, 1.0);
    NumberSettings polarRandomization = new NumberSettings("Polar randomization", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 7.0, 0.0, 10.0, 1.0);
    NumberSettings polarRandomizationSpeed = new NumberSettings("Polar random speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 7.0, 0.0, 10.0, 1.0);
    BooleanSettings polarSmooth = new BooleanSettings("Polar smooth", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), true);
    BooleanSettings polarDoubleNoise = new BooleanSettings("Polar double noise", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), true);
    public BooleanSettings fakeRotations = new BooleanSettings("Fake rotations", true);
    public NumberSettings minYawSpeed = new NumberSettings("Min Yaw Speed", () -> this.rotationMode.getMode().contains("Normal"), 50.0, 10.0, 180.0, 1.0);
    public NumberSettings maxYawSpeed = new NumberSettings("Max Yaw Speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Normal"), 50.0, 10.0, 180.0, 1.0);
    public NumberSettings minPitchSpeed = new NumberSettings("Min Pitch Speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Normal"), 50.0, 10.0, 180.0, 1.0);
    public NumberSettings maxPitchSpeed = new NumberSettings("Max Pitch Speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Normal"), 50.0, 10.0, 180.0, 1.0);
    public BooleanSettings newCombat = new BooleanSettings("New Combat", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), false);
    public ModeSettings cpsMode = new ModeSettings("CPS Mode", () -> !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), new String[]{"Normal", "Advanced"});
    public NumberSettings minCPS = new NumberSettings("Min CPS", () -> this.cpsMode.getMode().equalsIgnoreCase("Normal") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), 13.0, 1.0, 20.0, 1.0);
    public NumberSettings maxCPS = new NumberSettings("Max CPS", () -> this.cpsMode.getMode().equalsIgnoreCase("Normal") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), 13.0, 1.0, 20.0, 1.0);
    public NumberSettings minReduceCPS = new NumberSettings("Min Reduce CPS", () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), 20.0, 1.0, 40.0, 1.0);
    public NumberSettings maxReduceCPS = new NumberSettings("Max Reduce CPS", () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), 20.0, 1.0, 40.0, 1.0);
    public NumberSettings minNormalCPS = new NumberSettings("Min Normal CPS", () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), 20.0, 0.0, 40.0, 1.0);
    public NumberSettings maxNormalCPS = new NumberSettings("Max Normal CPS", () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), 20.0, 0.0, 40.0, 1.0);
    public BooleanSettings forceHit = new BooleanSettings("Force Hit", () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), true);
    public BooleanSettings swingInRange = new BooleanSettings("Swing In Range", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), true);
    public ModeSettings MoveFix = new ModeSettings("Move Fix", new String[]{"Legit", "Silent", "Off"});
    public NumberSettings Range = new NumberSettings("Range", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), 4.0, 3.0, 8.0, 0.01);
    public NumberSettings interactRange = new NumberSettings("Interact Range", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), 4.0, 3.0, 8.0, 0.01);
    public BooleanSettings raytrace = new BooleanSettings("Ray Trace", true);
    public ModeSettings switchMode = new ModeSettings("Switch", new String[]{"Timer", "Hurt time", "Off"});
    public NumberSettings switchDelay = new NumberSettings("Switch Delay", () -> this.switchMode.getMode().equalsIgnoreCase("Timer"), 100.0, 10.0, 1000.0, 1.0);
    public NumberSettings fov = new NumberSettings("Fov", 360.0, 10.0, 360.0, 10.0);
    public BooleanSettings disableOnDeath = new BooleanSettings("Disable on Death", true);
    public BooleanSettings TroughWalls = new BooleanSettings("Trough Walls", false);
    public BooleanSettings closedInventory = new BooleanSettings("Closed Inventory", false);
    public BooleanSettings attackInvisible = new BooleanSettings("Attack Invisible", false);
    public BooleanSettings attackDead = new BooleanSettings("Attack Dead", false);

    public KillAuraModule() {
        this.noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        this.addSettings(this.mode, this.Targets, this.sort);
        this.addSettings(this.autoBlock);
        this.addSettings(this.Range);
        this.addSettings(this.interactRange);
        this.addSettings(this.rotationMode, this.polarMinSpeed, this.polarMaxSpeed, this.polarRandomization, this.polarRandomizationSpeed, this.polarSmooth, this.polarDoubleNoise);
        this.addSettings(this.fakeRotations);
        this.addSettings(this.minYawSpeed);
        this.addSettings(this.maxYawSpeed);
        this.addSettings(this.minPitchSpeed);
        this.addSettings(this.maxPitchSpeed);
        this.addSettings(this.newCombat);
        this.addSettings(this.cpsMode);
        this.addSettings(this.minCPS, this.maxCPS);
        this.addSettings(this.minReduceCPS, this.maxReduceCPS, this.minNormalCPS, this.maxNormalCPS);
        this.addSettings(this.forceHit);
        this.addSettings(this.swingInRange);
        this.addSettings(this.MoveFix);
        this.addSettings(this.raytrace);
        this.addSettings(this.TroughWalls);
        this.addSettings(this.switchMode);
        this.addSettings(this.switchDelay);
        this.addSettings(this.fov);
        this.addSettings(this.disableOnDeath);
        this.addSettings(this.closedInventory);
        this.addSettings(this.attackInvisible);
        this.addSettings(this.attackDead);
    }

    @Override
    public void onDisable() {
        this.allowedToWork = false;
        this.mc.timer.timerSpeed = 1.0f;
        this.target = null;
        this.unBlock();
        SmoothRotationModule smoothRotation = (SmoothRotationModule)Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class);
        if (!smoothRotation.isEnabled() || !smoothRotation.ka.isEnabled()) {
            RotationUtils.customRots = false;
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
        }
        if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
            if (this.isBlinking) {
                this.stopBlinking();
            }
            if (this.isBlocking) {
                this.unBlockSword();
            }
        }
    }

    @Override
    public void onEnable() {
        this.intaveBlockTicks = 0;
        this.target = null;
        RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
        RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
        this.allowedToWork = false;
        this.blockingStatus = false;
        this.canSnapRotation = false;
        if (this.canWork()) {
            this.calculateCPS();
            this.calculateRots();
        }
        this.attackTimes = 0;
        this.attackTick = 0;
    }

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
            if (this.mc.isIntegratedServerRunning()) {
                return;
            }
            if (this.isBlinking && !e.isCancelled()) {
                if (NON_CANCELABLE_PACKETS.contains(e.getPacket().getClass())) {
                    return;
                }
                e.setCancelled(true);
                packetList.add(e.getPacket());
            }
            if (this.mc.thePlayer == null) {
                this.stopBlinking();
            }
        }
    }

    @EventListener
    public void update(EventTick e) {
        this.setInfo(this.switchMode.getMode().equalsIgnoreCase("Off") ? "Single" : "Switch");
        if ((this.mc.thePlayer.getHealth() <= 0.0f || this.mc.thePlayer.ticksExisted <= 5) && this.disableOnDeath.isEnabled()) {
            this.toggle();
            return;
        }
    }

    @EventListener
    public void onGameLoop(EventGameLoop e) {
        this.calculateCPS();
    }

    @EventListener
    public void onClick(EventClick e) {
        if (this.canWork()) {
            this.calculateCPS();
            this.calculateRots();
            this.attackLoop();
        }
        ++this.attackTick;
    }

    @EventListener
    public void onTimeDelay(EventTimeDelay e) {
        if (this.attackTick != 0) {
            this.attackTick = 0;
            return;
        }
        if (this.canWork()) {
            this.calculateCPS();
            this.calculateRots();
            this.attackLoop();
        }
    }

    @EventListener
    public void onRotationRender(EventRenderRotation e) {
        block20: {
            if (!this.allowedToWork || !RotationUtils.customRots) break block20;
            switch (this.rotationMode.getMode().toLowerCase()) {
                case "polar snap legit": 
                case "polar noise": 
                case "polar snap": 
                case "polar sin": {
                    if (this.fakeRotations.isEnabled()) {
                        e.setYaw(this.fakePolarYaw);
                        e.setYaw(this.fakePolarPitch);
                        break;
                    }
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                    break;
                }
                case "normal": {
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                    break;
                }
                case "snap": {
                    if (!this.canSnapRotation) break;
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                }
            }
        }
        this.block(this.target, "Render");
    }

    @EventListener
    public void onLook(EventLook e) {
        block18: {
            if (!this.allowedToWork || !RotationUtils.customRots) break block18;
            EventLook event = e;
            switch (this.rotationMode.getMode().toLowerCase()) {
                case "normal": 
                case "snap": {
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                    break;
                }
                case "polar snap legit": 
                case "polar noise": 
                case "polar snap": 
                case "polar sin": {
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                }
            }
        }
    }

    @EventListener
    public void onJump(EventJump e) {
        block19: {
            if (!this.allowedToWork || this.MoveFix.getMode().equalsIgnoreCase("Off") || !RotationUtils.customRots) break block19;
            switch (this.rotationMode.getMode().toLowerCase()) {
                case "normal": {
                    e.setYaw(RotationUtils.serverYaw);
                    break;
                }
                case "polar snap legit": 
                case "polar noise": 
                case "polar snap": 
                case "polar sin": {
                    e.setYaw(RotationUtils.serverYaw);
                    break;
                }
                case "snap": {
                    if (!this.canSnapRotation) break;
                    e.setYaw(RotationUtils.serverYaw);
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        block19: {
            if (!this.allowedToWork || !RotationUtils.customRots) return;
            if (e.getType() != EventType.PRE) break block19;
            switch (this.rotationMode.getMode().toLowerCase()) {
                case "normal": {
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                    return;
                }
                case "polar snap legit": 
                case "polar noise": 
                case "polar snap": 
                case "polar sin": {
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                    return;
                }
                case "snap": {
                    if (!this.canSnapRotation) return;
                    e.setYaw(RotationUtils.serverYaw);
                    e.setPitch(RotationUtils.serverPitch);
                }
                default: {
                    return;
                }
            }
        }
        if (!this.allowedToWork) return;
        this.block(this.target, "Post");
    }

    @EventListener
    public void onMove(EventMoveFlying e) {
        block11: {
            if (!this.allowedToWork || !RotationUtils.customRots) break block11;
            switch (this.MoveFix.getMode().toLowerCase()) {
                case "legit": {
                    if (this.rotationMode.getMode().equalsIgnoreCase("Snap")) {
                        if (!this.canSnapRotation) break;
                        e.setYaw(RotationUtils.serverYaw);
                        break;
                    }
                    e.setYaw(RotationUtils.serverYaw);
                    break;
                }
                case "silent": {
                    if (this.mc.isSingleplayer()) break;
                    e.setCancelled(true);
                    MovementUtils.silentMoveFix(e);
                }
            }
        }
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void attackLoop() {
        if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
            this.hypixelAttackLoop();
            return;
        }
        if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            return;
        }
        this.block(this.target, "Before");
        MovingObjectPosition ray = this.mc.thePlayer.rayTraceCustom(3.0, this.mc.timer.renderPartialTicks, RotationUtils.serverYaw, RotationUtils.serverPitch);
        if (this.attackTimes > 0) {
            int attacks = this.attackTimes;
            if ((double)this.mc.thePlayer.getDistanceToEntity(this.target) <= this.Range.getValue() || EntityUtils.getDistanceToEntityBox(this.target) <= this.Range.getValue() || this.mc.objectMouseOver.entityHit == this.target || ray.entityHit == this.target || RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}) == this.target) {
                if (!this.rotationMode.getMode().equalsIgnoreCase("None")) {
                    int i = 0;
                    while (i < attacks) {
                        if (this.raytrace.isEnabled()) {
                            Entity rayTracedEntity = RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                            if (rayTracedEntity == null) {
                                rayTracedEntity = ray.entityHit;
                            }
                            if (rayTracedEntity == null) {
                                rayTracedEntity = this.mc.objectMouseOver.entityHit;
                            }
                            if (rayTracedEntity != null) {
                                if (rayTracedEntity instanceof EntityPlayer && EntityUtils.isInSameTeam((EntityPlayer)rayTracedEntity)) {
                                    return;
                                }
                                if (rayTracedEntity instanceof EntityPlayer && FriendsCommand.friends.contains(rayTracedEntity.getName())) {
                                    return;
                                }
                                if (rayTracedEntity != null && this.target != null) {
                                    EventAttack event = new EventAttack(this.target);
                                    Client.INSTANCE.getEventBus().call(event);
                                    this.block(rayTracedEntity, "Before Attack");
                                    AttackOrder.sendLegitFixedKillAuraAttack(this.mc.thePlayer, rayTracedEntity);
                                    event.setType(EventType.POST);
                                    Client.INSTANCE.getEventBus().call(event);
                                    this.block(this.target, "After Attack");
                                } else if (this.swingInRange.isEnabled() && !this.newCombat.isEnabled()) {
                                    this.mc.clickMouseEvent();
                                }
                            }
                        }
                        ++i;
                    }
                } else {
                    int i = 0;
                    while (i < attacks) {
                        this.block(this.target, "Before Attack");
                        EventAttack event = new EventAttack(this.target);
                        Client.INSTANCE.getEventBus().call(event);
                        AttackOrder.sendLegitFixedKillAuraAttack(this.mc.thePlayer, this.target);
                        event.setType(EventType.POST);
                        Client.INSTANCE.getEventBus().call(event);
                        this.block(this.target, "After Attack");
                        ++i;
                    }
                }
            } else {
                boolean shouldReset = true;
                if (((double)this.mc.thePlayer.getDistanceToEntity(this.target) <= this.interactRange.getValue() || EntityUtils.getDistanceToEntityBox(this.target) <= this.interactRange.getValue()) && this.swingInRange.isEnabled() && !this.newCombat.isEnabled()) {
                    int i = 0;
                    while (i < attacks) {
                        this.mc.clickMouseEvent();
                        --this.attackTimes;
                        shouldReset = false;
                        ++i;
                    }
                }
                if (shouldReset) {
                    this.attackTimes = 0;
                }
            }
            this.attackTimes = 0;
        }
        this.block(this.target, "After");
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void hypixelAttackLoop() {
        if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            return;
        }
        this.minYawSpeed.setValue(180.0);
        this.maxYawSpeed.setValue(180.0);
        this.minPitchSpeed.setValue(180.0);
        this.maxPitchSpeed.setValue(180.0);
        this.Range.setValue(3.2);
        this.interactRange.setValue(3.2);
        this.newCombat.setEnabled(false);
        this.rotationMode.setMode("Normal");
        this.swingInRange.setEnabled(false);
        MovingObjectPosition ray = this.mc.thePlayer.rayTraceCustom(3.0, this.mc.timer.renderPartialTicks, RotationUtils.serverYaw, RotationUtils.serverPitch);
        if (((double)this.mc.thePlayer.getDistanceToEntity(this.target) <= this.Range.getValue() || EntityUtils.getDistanceToEntityBox(this.target) <= this.Range.getValue() || this.mc.objectMouseOver.entityHit == this.target || ray.entityHit == this.target || RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}) == this.target) && this.target != null) {
            ++this.autoBlockTick;
            switch (this.autoBlockTick) {
                case 1: {
                    this.mc.playerController.syncCurrentPlayItem();
                    if (this.isBlinking) {
                        this.stopBlinking();
                        this.isBlinking = false;
                    }
                    EventAttack event = new EventAttack(this.target);
                    Client.INSTANCE.getEventBus().call(event);
                    this.mc.thePlayer.swingItem();
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)this.target, C02PacketUseEntity.Action.ATTACK));
                    event.setType(EventType.POST);
                    Client.INSTANCE.getEventBus().call(event);
                    this.blockSword(true);
                    break;
                }
                case 2: {
                    this.startBlinking();
                    this.isBlinking = true;
                    int oldSlot = this.mc.thePlayer.inventory.currentItem++;
                    this.mc.playerController.syncCurrentPlayItem();
                    this.isBlocking = false;
                    this.mc.thePlayer.inventory.currentItem = oldSlot;
                    this.autoBlockTick = 0;
                }
            }
        } else {
            if (this.autoBlockTick == 1) {
                this.autoBlockTick = 0;
            }
            if (this.isBlinking) {
                this.stopBlinking();
            }
            if (this.isBlocking) {
                this.unBlockSword();
            }
        }
        if (this.mc.thePlayer.getHeldItem() == null || !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
            if (this.autoBlockTick == 1) {
                this.autoBlockTick = 0;
            }
            if (this.isBlinking) {
                this.stopBlinking();
            }
            if (this.isBlocking) {
                this.unBlockSword();
            }
        }
    }

    public void calculateCPS() {
        if (this.newCombat.isEnabled()) {
            double delay = 4.0;
            if (this.mc.thePlayer.getHeldItem() != null) {
                Item item = this.mc.thePlayer.getHeldItem().getItem();
                if (item instanceof ItemSpade || item == Items.golden_axe || item == Items.diamond_axe || item == Items.wooden_hoe || item == Items.golden_hoe) {
                    delay = 20.0;
                }
                if (item == Items.wooden_axe || item == Items.stone_axe) {
                    delay = 25.0;
                }
                if (item instanceof ItemSword) {
                    delay = 12.0;
                }
                if (item instanceof ItemPickaxe) {
                    delay = 17.0;
                }
                if (item == Items.iron_axe) {
                    delay = 22.0;
                }
                if (item == Items.stone_hoe) {
                    delay = 10.0;
                }
                if (item == Items.iron_hoe) {
                    delay = 7.0;
                }
            }
            if (this.clickTimer.hasTimeElapsed(delay *= 50.0, true)) {
                ++this.attackTimes;
            }
        } else if (this.cpsMode.getMode().equalsIgnoreCase("Advanced")) {
            this.advancedClick();
        } else {
            this.normalClick();
        }
    }

    public void normalClick() {
        if (this.clickTimer.hasTimeElapsed(this.calculateCPS(this.minCPS.getValue(), this.maxCPS.getValue()), true)) {
            ++this.attackTimes;
            return;
        }
    }

    public void advancedClick() {
        int perfectHitHurtTime = 2;
        boolean stop = false;
        if (this.forceHit.isEnabled()) {
            Entity rayTracedEntity = RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
            if (this.raytrace.isEnabled()) {
                if ((rayTracedEntity == this.target || this.mc.objectMouseOver.entityHit == this.target) && this.target != null && rayTracedEntity instanceof EntityLivingBase) {
                    EntityLivingBase entity = (EntityLivingBase)rayTracedEntity;
                    if (entity.hurtTime <= perfectHitHurtTime) {
                        this.attackTimes = 1;
                        stop = true;
                    }
                }
            } else if (this.target != null && this.target.hurtTime <= perfectHitHurtTime) {
                this.attackTimes = 1;
                stop = true;
            }
        }
        if (!stop) {
            if (this.mc.thePlayer.hurtTime == 0) {
                if (this.clickTimer.hasTimeElapsed(this.calculateCPS(this.minNormalCPS.getValue(), this.maxNormalCPS.getValue()), true)) {
                    ++this.attackTimes;
                }
            } else if (this.clickTimer.hasTimeElapsed(this.calculateCPS(this.minReduceCPS.getValue(), this.maxReduceCPS.getValue()), true)) {
                ++this.attackTimes;
            }
        }
    }

    public double calculateCPS(double min, double max) {
        double minValue = min;
        double maxValue = max;
        if (minValue == 0.0 && maxValue == 0.0) {
            return 100000.0;
        }
        if (minValue > maxValue) {
            minValue = maxValue;
        }
        if (maxValue < minValue) {
            maxValue = minValue;
        }
        if (this.cpsRandomizationTimer.hasTimeElapsed(150.0, true)) {
            this.randomCPS = Math.round(minValue + new Random().nextDouble() * (maxValue - minValue));
        }
        double delay = 1000.0 / this.randomCPS;
        delay -= 3.0;
        delay = Math.round(delay);
        return delay;
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void calculateRots() {
        boolean wasSet = RotationUtils.customRots;
        if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            return;
        }
        switch (this.rotationMode.getMode().toLowerCase()) {
            case "normal": {
                double minYaw = this.minYawSpeed.getValue() / 2.0;
                double maxYaw = this.maxYawSpeed.getValue() / 2.0;
                double minPitch = this.minPitchSpeed.getValue() / 2.0;
                double maxPitch = this.maxPitchSpeed.getValue() / 2.0;
                if (minYaw > maxYaw) {
                    minYaw = maxYaw;
                }
                if (maxYaw < minYaw) {
                    maxYaw = minYaw;
                }
                if (minPitch > maxPitch) {
                    minPitch = maxPitch;
                }
                if (maxPitch < minPitch) {
                    maxPitch = minPitch;
                }
                if (this.rotationRandomizationTimer.hasTimeElapsed(150.0, true)) {
                    this.yawSpeed = minYaw + new Random().nextDouble() * (maxYaw - minYaw);
                    this.pitchSpeed = minPitch + new Random().nextDouble() * (maxPitch - minPitch);
                }
                if (this.yawSpeed < minYaw) {
                    this.yawSpeed = minYaw;
                }
                if (this.yawSpeed > maxYaw) {
                    this.yawSpeed = maxYaw;
                }
                if (this.pitchSpeed < minPitch) {
                    this.pitchSpeed = minPitch;
                }
                if (this.pitchSpeed > maxPitch) {
                    this.pitchSpeed = maxPitch;
                }
                float[] rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch, this.target, this.target.posX, this.target.posY, this.target.posZ, (float)this.yawSpeed, (float)this.pitchSpeed, true);
                rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                RotationUtils.serverYaw = rots[0];
                RotationUtils.serverPitch = rots[1];
                RotationUtils.customRots = true;
                break;
            }
            case "snap": {
                this.canSnapRotation = this.attackTimes > 0;
                if (this.canSnapRotation) {
                    float[] rots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY, this.target.posZ, true);
                    rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                    RotationUtils.serverYaw = rots[0];
                    RotationUtils.serverPitch = rots[1];
                    RotationUtils.customRots = true;
                    break;
                }
                RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                RotationUtils.customRots = false;
                break;
            }
            case "polar snap": {
                if (this.mc.objectMouseOver.entityHit == null && EntityUtils.getDistanceToEntityBox(this.target) <= 3.0) {
                    float turn = 180.0f;
                    double randomization = 7.0;
                    double randomizationSpeed = 9.0;
                    float[] rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch, this.target, this.target.posX, this.target.posY, this.target.posZ, turn, turn, false);
                    float noiseX1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed);
                    float noiseY1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0f;
                    float noiseX2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0f;
                    float noiseY2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 100.0f;
                    float noiseYaw = (float)((double)this.noise.GetNoise(noiseX1, noiseY1) * randomization);
                    float noisePitch = (float)((double)this.noise.GetNoise(noiseX2, noiseY2) * randomization);
                    rots[0] = rots[0] + noiseYaw;
                    rots[1] = rots[1] + noisePitch;
                    rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                    RotationUtils.serverYaw = rots[0];
                    RotationUtils.serverPitch = rots[1];
                }
                RotationUtils.customRots = true;
                float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                this.fakePolarYaw = fakeRots[0];
                this.fakePolarPitch = fakeRots[1];
                break;
            }
            case "polar noise": {
                float minSpeed = (float)Math.min(this.polarMinSpeed.getValue(), this.polarMaxSpeed.getValue());
                float maxSpeed = (float)Math.max(this.polarMinSpeed.getValue(), this.polarMaxSpeed.getValue());
                float turn = RandomUtils.nextFloat(minSpeed, maxSpeed);
                double randomization = this.polarRandomization.getValue();
                double randomizationSpeed = this.polarRandomizationSpeed.getValue();
                float[] rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch, this.target, this.target.posX, this.target.posY, this.target.posZ, turn, turn, false);
                float noiseX1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed);
                float noiseY1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0f;
                float noiseX2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0f;
                float noiseY2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 100.0f;
                float noiseYaw = (float)((double)this.noise.GetNoise(noiseX1, noiseY1) * randomization);
                float noisePitch = (float)((double)this.noise.GetNoise(noiseX2, noiseY2) * randomization);
                rots[0] = rots[0] + (noiseYaw *= Math.max(1.0f, Math.min(3.0f, this.mc.thePlayer.getDistanceToEntity(this.target)) / 3.0f));
                rots[1] = rots[1] + (noisePitch *= Math.max(1.0f, Math.min(3.0f, this.mc.thePlayer.getDistanceToEntity(this.target)) / 3.0f));
                if (this.polarDoubleNoise.isEnabled()) {
                    float noiseX12 = this.mc.thePlayer.ticksExisted * 18;
                    float noiseY12 = (float)(this.mc.thePlayer.ticksExisted * 18) + 150.0f;
                    float noiseX22 = (float)(this.mc.thePlayer.ticksExisted * 18) + 150.0f;
                    float noiseY22 = (float)(this.mc.thePlayer.ticksExisted * 18) + 200.0f;
                    float noiseYaw2 = this.noise.GetNoise(noiseX12, noiseY12) * 2.0f;
                    float noisePitch2 = this.noise.GetNoise(noiseX22, noiseY22) * 2.0f;
                    rots[0] = rots[0] + noiseYaw2;
                    rots[1] = rots[1] + noisePitch2;
                }
                if (this.polarSmooth.isEnabled()) {
                    float smoothness = 2.0f;
                    rots[0] = (RotationUtils.serverYaw * smoothness + (rots[0] - RotationUtils.serverYaw)) / smoothness;
                    rots[1] = (RotationUtils.serverPitch * smoothness + (rots[1] - RotationUtils.serverPitch)) / smoothness;
                }
                rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                RotationUtils.serverYaw = rots[0];
                RotationUtils.serverPitch = rots[1];
                RotationUtils.customRots = true;
                float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                this.fakePolarYaw = fakeRots[0];
                this.fakePolarPitch = fakeRots[1];
                break;
            }
            case "polar snap legit": {
                float[] rots = new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch};
                float turn = RandomUtils.nextInt(10, 60);
                double randomization = 3.0;
                double randomizationSpeed = 10.0;
                if (this.mc.objectMouseOver.entityHit == null && EntityUtils.getDistanceToEntityBox(this.target) <= 3.0) {
                    rots = RotationUtils.getNormalAuraRotations(RotationUtils.serverYaw, RotationUtils.serverPitch, this.target, this.target.posX, this.target.posY, this.target.posZ, turn, turn, false);
                    float smoothness = 2.0f;
                    rots[0] = (RotationUtils.serverYaw * smoothness + (rots[0] - RotationUtils.serverYaw)) / smoothness;
                    rots[1] = (RotationUtils.serverPitch * smoothness + (rots[1] - RotationUtils.serverPitch)) / smoothness;
                }
                float noiseX1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed);
                float noiseY1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0f;
                float noiseX2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0f;
                float noiseY2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 100.0f;
                float noiseYaw = (float)((double)this.noise.GetNoise(noiseX1, noiseY1) * randomization);
                float noisePitch = (float)((double)this.noise.GetNoise(noiseX2, noiseY2) * randomization);
                rots[0] = rots[0] + noiseYaw;
                rots[1] = rots[1] + noisePitch;
                rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                RotationUtils.serverYaw = rots[0];
                RotationUtils.serverPitch = rots[1];
                RotationUtils.customRots = true;
                float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                this.fakePolarYaw = fakeRots[0];
                this.fakePolarPitch = fakeRots[1];
            }
            case "polar sin": {
                float[] rots = RotationUtils.getNormalAuraRotations((float)((double)RotationUtils.serverYaw - Math.sin(this.mc.thePlayer.ticksExisted / 2) * 4.0), (float)((double)RotationUtils.serverPitch - Math.cos(this.mc.thePlayer.ticksExisted / 2) * 4.0), this.target, this.target.posX, this.target.posY, this.target.posZ, 55.0f, 40.0f, true);
                rots[0] = rots[0] + (float)(Math.sin(this.mc.thePlayer.ticksExisted / 2) * 4.0);
                rots[1] = rots[1] + (float)(Math.cos(this.mc.thePlayer.ticksExisted / 2) * 4.0);
                rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                RotationUtils.serverYaw = rots[0];
                RotationUtils.serverPitch = rots[1];
                RotationUtils.customRots = true;
                float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                this.fakePolarYaw = fakeRots[0];
                this.fakePolarPitch = fakeRots[1];
            }
        }
        if (RotationUtils.serverPitch > 90.0f) {
            RotationUtils.serverPitch = 90.0f;
        }
        if (RotationUtils.serverPitch < -90.0f) {
            RotationUtils.serverPitch = -90.0f;
        }
        if (!wasSet && RotationUtils.customRots && this.rotationMode.getMode().contains("Polar")) {
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
        }
    }

    public boolean canWork() {
        if (this.closedInventory.isEnabled()) {
            InvManagerModule invManager = (InvManagerModule)Client.INSTANCE.getModuleManager().getModule(InvManagerModule.class);
            AutoArmorModule autoArmor = (AutoArmorModule)Client.INSTANCE.getModuleManager().getModule(AutoArmorModule.class);
            AutoHealModule autoHeal = (AutoHealModule)Client.INSTANCE.getModuleManager().getModule(AutoHealModule.class);
            AntiFireModule antiFire = (AntiFireModule)Client.INSTANCE.getModuleManager().getModule(AntiFireModule.class);
            NoFallModule noFall = (NoFallModule)Client.INSTANCE.getModuleManager().getModule(NoFallModule.class);
            ScaffoldModule scaffold = (ScaffoldModule)Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class);
            AnnoyerModule annoyer = (AnnoyerModule)Client.INSTANCE.getModuleManager().getModule(AnnoyerModule.class);
            AutoRodModule autoRod = (AutoRodModule)Client.INSTANCE.getModuleManager().getModule(AutoRodModule.class);
            if (this.mc.currentScreen != null) {
                if (this.allowedToWork) {
                    RotationUtils.customRots = false;
                    this.unBlock();
                }
                this.allowedToWork = false;
                this.attackTimes = 0;
                return false;
            }
            if ((invManager.mode.getMode().equalsIgnoreCase("Spoof") || autoArmor.mode.getMode().equalsIgnoreCase("Spoof") || autoHeal.mode.getMode().equalsIgnoreCase("Spoof")) && InventoryUtils.isInventoryOpen || autoHeal.cancelAura || antiFire.canWork || noFall.canWork || annoyer.cancel || autoRod.cancel || scaffold.isEnabled()) {
                if (this.allowedToWork) {
                    RotationUtils.customRots = false;
                    this.unBlock();
                }
                this.allowedToWork = false;
                this.attackTimes = 0;
                return false;
            }
        }
        this.target = EntityUtils.getTarget(Math.max(this.Range.getValue(), this.interactRange.getValue()), this.Targets.getMode(), this.switchMode.getMode(), (int)this.switchDelay.getValue(), Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(), this.TroughWalls.isEnabled(), this.attackInvisible.isEnabled(), this.attackDead.isEnabled());
        if (this.target == null) {
            this.target = EntityUtils.getTargetBox(Math.max(this.Range.getValue(), this.interactRange.getValue()), this.Targets.getMode(), this.switchMode.getMode(), (int)this.switchDelay.getValue(), Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(), this.TroughWalls.isEnabled(), this.attackInvisible.isEnabled(), this.attackDead.isEnabled());
        }
        if (this.target == null) {
            if (this.allowedToWork) {
                if (!Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class).isEnabled()) {
                    RotationUtils.customRots = false;
                }
                this.unBlock();
            }
            this.allowedToWork = false;
            this.attackTimes = 0;
            return false;
        }
        if (!this.allowedToWork) {
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
        }
        this.allowedToWork = true;
        return true;
    }

    public void unBlock() {
        if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
            if (this.autoBlockTick == 1) {
                this.autoBlockTick = 0;
            }
            if (this.isBlinking) {
                this.stopBlinking();
            }
            if (this.isBlocking) {
                this.unBlockSword();
            }
            return;
        }
        if (this.autoBlock.getMode().equalsIgnoreCase("Fake")) {
            return;
        }
        this.mc.gameSettings.keyBindUseItem.pressed = false;
        this.blockingStatus = false;
    }

    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void block(Entity ent, String timing) {
        block16: {
            if (this.mc.thePlayer == null || this.mc.theWorld == null || !this.allowedToWork) {
                return;
            }
            if (this.mc.thePlayer.getHeldItem() == null || this.mc.thePlayer.getHeldItem().getItem() == null || !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break block16;
            switch (this.autoBlock.getMode().toLowerCase()) {
                case "legit": {
                    if (!timing.equalsIgnoreCase("Before")) break;
                    if (this.mc.thePlayer.hurtTime >= 6) {
                        this.mc.gameSettings.keyBindUseItem.pressed = true;
                        this.blockingStatus = true;
                        break;
                    }
                    if (this.mc.thePlayer.hurtTime <= 0) break;
                    this.mc.gameSettings.keyBindUseItem.pressed = false;
                    this.blockingStatus = false;
                    break;
                }
                case "vanilla": {
                    this.mc.gameSettings.keyBindUseItem.pressed = true;
                    this.blockingStatus = true;
                    break;
                }
                case "fake": {
                    if (!this.blockingStatus && this.mc.gameSettings.keyBindUseItem.pressed) {
                        this.mc.gameSettings.keyBindUseItem.pressed = false;
                    }
                    this.mc.thePlayer.getHeldItem().useItemRightClick(this.mc.theWorld, this.mc.thePlayer);
                    this.blockingStatus = true;
                }
            }
        }
    }

    public void startBlinking() {
        if (this.mc.isIntegratedServerRunning()) {
            return;
        }
        this.isBlinking = true;
    }

    public void stopBlinking() {
        if (this.mc.isIntegratedServerRunning()) {
            return;
        }
        this.isBlinking = false;
        packetList.forEach(this.mc.thePlayer.sendQueue::addToSendQueue);
        packetList.clear();
    }

    public void blockSword(boolean interact) {
        boolean canBlock;
        boolean bl = canBlock = !this.isBlocking && this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
        if (canBlock) {
            if (interact) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)this.target, C02PacketUseEntity.Action.INTERACT));
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getHeldItem()));
        }
        this.isBlocking = true;
    }

    public void unBlockSword() {
        boolean canBlock;
        boolean bl = canBlock = this.isBlocking && this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
        if (canBlock) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        this.isBlocking = false;
    }
}
