package xyz.cucumber.base.module.feat.player;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.PLAYER, description="Allows you to disable anticheats", name="Disabler", priority=ArrayPriority.HIGH)
@BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
public class DisablerModule
extends Mod {
    public boolean disabled;
    public boolean move;
    public boolean attack;
    public int counter;
    public int balance;
    public int maxBalance;
    public double difference;
    public long lastSetBack;
    private int currentDelay = 5000;
    private int currentBuffer = 4;
    private int currentDec = -1;
    public Timer timer = new Timer();
    public Timer timer1 = new Timer();
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Polar full", "Hypixel test", "Hypixel Motion", "Intave Cloud Check", "Intave old", "Timer Range", "Spectate 1", "Spectate 2", "Verus", "Minemen club", "Mushmc"});
    public BooleanSettings expandScaffold = new BooleanSettings("Expand Scaffold", true);
    public BooleanSettings intaveReach = new BooleanSettings("Intave Reach", false);
    public BooleanSettings intaveReachStrong = new BooleanSettings("Intave Reach Strong", false);
    public BooleanSettings autoAura = new BooleanSettings("Auto Aura", false);
    public ArrayList<Packet> packets = new ArrayList();
    public ArrayList<Packet> packets1 = new ArrayList();
    public ArrayList<Packet> packets2 = new ArrayList();
    private final ArrayList<Packet<?>> funPackets = new ArrayList();
    private final ConcurrentHashMap<Packet<?>, Long> pingSpoofPackets = new ConcurrentHashMap();

    public DisablerModule() {
        this.addSettings(this.mode, this.expandScaffold, this.intaveReach, this.intaveReachStrong, this.autoAura);
    }

    @Override
    public void onEnable() {
        this.timer.reset();
        this.disabled = false;
        this.move = false;
        this.packets.clear();
        this.packets1.clear();
        this.funPackets.clear();
        switch (this.mode.getMode().toLowerCase()) {
            case "verus experimental": {
                Client.INSTANCE.getCommandManager().sendChatMessage("idk what to say");
                break;
            }
            case "intave old": {
                if (!this.autoAura.isEnabled()) break;
                KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
                ka.Range.setValue(6.0);
                ka.interactRange.setValue(6.0);
            }
        }
    }

    @Override
    public void onDisable() {
        this.counter = 0;
        this.mc.timer.timerSpeed = 1.0f;
        this.balance = 0;
        try {
            while (this.packets.size() > 0) {
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                this.packets.remove(this.packets.get(0));
            }
            while (this.packets1.size() > 0) {
                this.packets1.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
                this.packets1.remove(this.packets1.get(0));
            }
            while (this.packets2.size() > 0) {
                this.packets2.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
                this.packets2.remove(this.packets2.get(0));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        switch (this.mode.getMode().toLowerCase()) {
            case "verus experimental": {
                Client.INSTANCE.getCommandManager().sendChatMessage("idk what to say");
                break;
            }
            case "intave old": {
                if (!this.autoAura.isEnabled()) break;
                KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
                ka.Range.setValue(3.0);
                ka.interactRange.setValue(4.0);
            }
        }
    }

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        switch (this.mode.getMode().toLowerCase()) {
            case "polar full": {
                if (!(e.getPacket() instanceof C0FPacketConfirmTransaction)) break;
                e.setCancelled(true);
                break;
            }
            case "hypixel test": {
                if (this.timer.hasTimeElapsed(2000.0, false) && !this.funPackets.isEmpty()) {
                    for (Packet<?> packet : this.funPackets) {
                    }
                    this.timer.reset();
                }
                if (!(e.getPacket() instanceof C0FPacketConfirmTransaction) && (!(e.getPacket() instanceof C00PacketKeepAlive) || this.timer.hasTimeElapsed(2000.0, false))) break;
                this.funPackets.add(e.getPacket());
                e.setCancelled(true);
                break;
            }
            case "spectate 1": {
                this.packets.add(e.getPacket());
                e.setCancelled(true);
                break;
            }
            case "spectate 2": {
                if (!this.disabled) break;
                this.packets.add(e.getPacket());
                e.setCancelled(true);
                break;
            }
            case "mushmc": {
                if (!(e.getPacket() instanceof C03PacketPlayer) || !this.timer.hasTimeElapsed(1200.0, true)) break;
                C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
                packet.y -= 9.99999999E8;
                this.disabled = true;
                break;
            }
            case "timer range": {
                C03PacketPlayer packet;
                if (!(e.getPacket() instanceof C03PacketPlayer) || (packet = (C03PacketPlayer)e.getPacket()).isMoving() || packet.getRotating() || this.mc.thePlayer.isUsingItem() || Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) break;
                e.setCancelled(true);
                break;
            }
            case "intave cloud check": {
                if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                    e.setCancelled(true);
                    this.packets.add(e.getPacket());
                }
                try {
                    while (this.packets.size() > 50) {
                        this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                        this.packets.remove(0);
                    }
                }
                catch (Exception packet) {}
                break;
            }
            case "intave old": {
                EntityLivingBase entity;
                TeamsModule teams;
                KillAuraModule killAura;
                if (e.getPacket() instanceof C19PacketResourcePackStatus) {
                    e.setCancelled(true);
                }
                if (!this.intaveReach.isEnabled()) break;
                if (e.getPacket() instanceof C02PacketUseEntity || e.getPacket() instanceof C0APacketAnimation) {
                    killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
                    teams = (TeamsModule)Client.INSTANCE.getModuleManager().getModule(TeamsModule.class);
                    entity = EntityUtils.getTargetBox(8.0, killAura.Targets.getMode(), killAura.switchMode.getMode(), 500, teams.isEnabled(), killAura.TroughWalls.isEnabled(), killAura.attackDead.isEnabled(), killAura.attackInvisible.isEnabled());
                    if (entity == null) {
                        e.setCancelled(true);
                        return;
                    }
                    if (EntityUtils.getDistanceToEntityBox(entity) > 3.0 && killAura.isEnabled()) {
                        e.setCancelled(true);
                    }
                }
                if (!(e.getPacket() instanceof C03PacketPlayer) || !this.move) break;
                this.move = false;
                this.attack = true;
                killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
                teams = (TeamsModule)Client.INSTANCE.getModuleManager().getModule(TeamsModule.class);
                entity = EntityUtils.getTargetBox(8.0, killAura.Targets.getMode(), killAura.switchMode.getMode(), 500, teams.isEnabled(), killAura.TroughWalls.isEnabled(), killAura.attackDead.isEnabled(), killAura.attackInvisible.isEnabled());
                if (entity == null) {
                    return;
                }
                e.setCancelled(true);
                float[] rots = RotationUtils.getRotationsFromPositionToPosition(entity.posX, entity.posY, entity.posZ, this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                float yaw = rots[0];
                float pitch = rots[1];
                int i = 0;
                while (i < (this.intaveReachStrong.isEnabled() ? 20 : 2)) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(entity.posX, entity.posY, entity.posZ, yaw, pitch, true));
                    ++i;
                }
                this.attack = true;
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0APacketAnimation());
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
                break;
            }
            case "verus experimental": {
                if (!(e.getPacket() instanceof C03PacketPlayer)) break;
                if (this.mc.thePlayer.isRiding()) {
                    ((C03PacketPlayer)e.getPacket()).onGround = false;
                }
                if (!this.disabled) break;
                ((C03PacketPlayer)e.getPacket()).onGround = false;
                break;
            }
            case "verus": {
                if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                    e.setCancelled(true);
                    this.packets.add(e.getPacket());
                }
                while (this.packets.size() > 200) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                    this.packets.remove(0);
                    if (this.disabled) continue;
                    this.disabled = true;
                    Client.INSTANCE.getCommandManager().sendChatMessage("Verus is now disabled");
                }
                if (this.mc.thePlayer.isUsingItem()) {
                    C0BPacketEntityAction packet;
                    if (this.move || !this.mc.thePlayer.isSprinting()) break;
                    this.move = true;
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    if (!(e.getPacket() instanceof C0BPacketEntityAction) || (packet = (C0BPacketEntityAction)e.getPacket()).getAction() != C0BPacketEntityAction.Action.START_SPRINTING && packet.getAction() != C0BPacketEntityAction.Action.STOP_SPRINTING) break;
                    e.setCancelled(true);
                    break;
                }
                this.move = false;
            }
        }
    }

    @EventListener
    public void onReceivePacket(EventReceivePacket e) {
        switch (this.mode.getMode().toLowerCase()) {
            case "spectate 1": {
                if (e.getPacket() instanceof S08PacketPlayerPosLook && !this.move) {
                    S08PacketPlayerPosLook p = (S08PacketPlayerPosLook)e.getPacket();
                    e.setCancelled(true);
                    this.mc.thePlayer.setPositionAndRotation(p.x, p.y, p.z, p.yaw, p.pitch);
                    if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_16)) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(p.x, p.y, p.z, p.yaw, p.pitch, false));
                    } else {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.getEntityBoundingBox().minY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, false));
                    }
                    e.setCancelled(true);
                    this.move = true;
                    return;
                }
                if (e.getPacket() instanceof S39PacketPlayerAbilities && this.mc.thePlayer.capabilities.allowFlying) {
                    if (!this.disabled) {
                        this.timer.reset();
                    }
                    this.disabled = true;
                    e.setCancelled(true);
                    this.packets2.add(e.getPacket());
                    return;
                }
                e.setCancelled(true);
                this.packets1.add(e.getPacket());
                break;
            }
            case "mushmc": {
                if (!(e.getPacket() instanceof S08PacketPlayerPosLook) || !this.disabled) break;
                this.disabled = false;
                S08PacketPlayerPosLook p = (S08PacketPlayerPosLook)e.getPacket();
                break;
            }
            case "verus experimental": {
                if (e.getPacket() instanceof S07PacketRespawn || e.getPacket() instanceof S01PacketJoinGame) {
                    this.packets.clear();
                    this.counter = 0;
                    this.disabled = false;
                }
                if (e.getPacket() instanceof S13PacketDestroyEntities) {
                    if (((S13PacketDestroyEntities)e.getPacket()).getEntityIDs().length != 100) {
                        int[] nArray = ((S13PacketDestroyEntities)e.getPacket()).getEntityIDs();
                        int n = nArray.length;
                        int n2 = 0;
                        while (n2 < n) {
                            int entityID = nArray[n2];
                            if (entityID == this.mc.thePlayer.ridingEntity.getEntityId()) {
                                this.mc.timer.timerSpeed = 1.0f;
                                Client.INSTANCE.getCommandManager().sendChatMessage("Verus has been disabled");
                                this.disabled = true;
                            }
                            ++n2;
                        }
                    } else {
                        int[] nArray = ((S13PacketDestroyEntities)e.getPacket()).getEntityIDs();
                        int n = nArray.length;
                        int n3 = 0;
                        while (n3 < n) {
                            int entityID = nArray[n3];
                            if (entityID == this.mc.thePlayer.ridingEntity.getEntityId()) {
                                Client.INSTANCE.getCommandManager().sendChatMessage("Verus has been disabled");
                                int i = 0;
                                while (i < 20) {
                                    Client.INSTANCE.getCommandManager().sendChatMessage("Verus disabler have not enabled successfully");
                                    ++i;
                                }
                            }
                            ++n3;
                        }
                    }
                }
                if (!(e.getPacket() instanceof S1BPacketEntityAttach) || ((S1BPacketEntityAttach)e.getPacket()).getEntityId() != this.mc.thePlayer.getEntityId() || ((S1BPacketEntityAttach)e.getPacket()).getVehicleEntityId() <= 0) break;
                this.counter = 0;
            }
        }
    }

    @EventListener
    public void onTick(EventTick e) {
        this.setInfo(this.mode.getMode());
        switch (this.mode.getMode().toLowerCase()) {
            case "spectate 1": {
                if (!this.timer.hasTimeElapsed(5000.0, false)) break;
                this.move = false;
                this.disabled = false;
                try {
                    while (this.packets.size() > 0) {
                        this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                        this.packets.remove(this.packets.get(0));
                    }
                    while (this.packets1.size() > 0) {
                        this.packets1.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
                        this.packets1.remove(this.packets1.get(0));
                    }
                    while (this.packets2.size() > 0) {
                        this.packets2.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
                        this.packets2.remove(this.packets2.get(0));
                    }
                }
                catch (Exception exception) {}
                break;
            }
            case "hypixel motion": {
                if (this.mc.thePlayer.ticksExisted >= 150) break;
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Math.random() / Math.random() * Math.random() / 3.0, this.mc.thePlayer.posZ);
                break;
            }
            case "verus experimental": {
                ++this.counter;
                if (this.mc.thePlayer.ridingEntity == null) break;
                for (Entity entity : this.mc.theWorld.getLoadedEntityList()) {
                    double deltaZ;
                    double deltaY;
                    double deltaX;
                    if (!(entity instanceof EntityBoat) || Math.sqrt((deltaX = entity.posX - this.mc.thePlayer.posX) * deltaX + (deltaY = entity.posY - this.mc.thePlayer.posY) * deltaY + (deltaZ = entity.posZ - this.mc.thePlayer.posZ) * deltaZ) >= 5.0) continue;
                    this.mc.timer.timerSpeed = 0.3f;
                    if (entity == this.mc.thePlayer.ridingEntity) {
                        this.mc.timer.timerSpeed = 0.3f;
                        continue;
                    }
                    int item = -1;
                    double highest = 0.0;
                    int i = 36;
                    while (i < 45) {
                        if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && (double)InventoryUtils.getItemDamage(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > highest) {
                            highest = InventoryUtils.getItemDamage(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                            item = i - 36;
                        }
                        ++i;
                    }
                    if (highest == 0.0) {
                        item = -1;
                    }
                    if (item == -1) {
                        Client.INSTANCE.getCommandManager().sendChatMessage("You need to hold better weapon");
                        return;
                    }
                    if (item != this.mc.thePlayer.inventory.currentItem) {
                        this.mc.thePlayer.inventory.currentItem = item;
                        return;
                    }
                    if (this.mc.thePlayer.getCurrentEquippedItem() == null || (double)InventoryUtils.getItemDamage(this.mc.thePlayer.getCurrentEquippedItem()) < 4.0) {
                        Client.INSTANCE.getCommandManager().sendChatMessage("You need to hold better weapon");
                        return;
                    }
                    RotationUtils.customRots = true;
                    RotationUtils.serverPitch = 90.0f;
                    this.mc.thePlayer.swingItem();
                    this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.thePlayer.ridingEntity);
                    this.mc.thePlayer.swingItem();
                    this.mc.playerController.attackEntity(this.mc.thePlayer, entity);
                }
                break;
            }
        }
    }

    /*
     * Exception decompiling
     */
    @EventListener
    public void onWorldChange(EventWorldChange e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[6] lbl43 : CaseStatement: default:
, @NONE, blocks:[6] lbl43 : CaseStatement: default:
]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:25)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:8)
         *     at java.util.TimSort.countRunAndMakeAscending(TimSort.java:360)
         *     at java.util.TimSort.sort(TimSort.java:220)
         *     at java.util.Arrays.sort(Arrays.java:1512)
         *     at java.util.ArrayList.sort(ArrayList.java:1464)
         *     at java.util.Collections.sort(Collections.java:177)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.buildSwitchCases(SwitchReplacer.java:271)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitch(SwitchReplacer.java:258)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:517)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at me.coley.recaf.decompile.cfr.CfrDecompiler.decompile(CfrDecompiler.java:71)
         *     at me.coley.recaf.plugin.decompile.DecompilePanel.decompile(DecompilePanel.java:131)
         *     at me.coley.recaf.plugin.decompile.DecompilePanel.lambda$startDecompile$3(DecompilePanel.java:94)
         *     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    @EventListener
    public void onHit(EventHit e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[4] lbl30 : CaseStatement: default:
, @NONE, blocks:[4] lbl30 : CaseStatement: default:
]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:25)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:8)
         *     at java.util.TimSort.countRunAndMakeAscending(TimSort.java:360)
         *     at java.util.TimSort.sort(TimSort.java:220)
         *     at java.util.Arrays.sort(Arrays.java:1512)
         *     at java.util.ArrayList.sort(ArrayList.java:1464)
         *     at java.util.Collections.sort(Collections.java:177)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.buildSwitchCases(SwitchReplacer.java:271)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitch(SwitchReplacer.java:258)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:517)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at me.coley.recaf.decompile.cfr.CfrDecompiler.decompile(CfrDecompiler.java:71)
         *     at me.coley.recaf.plugin.decompile.DecompilePanel.decompile(DecompilePanel.java:131)
         *     at me.coley.recaf.plugin.decompile.DecompilePanel.lambda$startDecompile$3(DecompilePanel.java:94)
         *     at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean intaveIncoming(Packet packet) {
        return packet instanceof S00PacketKeepAlive || packet instanceof S32PacketConfirmTransaction || packet instanceof S08PacketPlayerPosLook || packet instanceof S12PacketEntityVelocity || packet instanceof S27PacketExplosion;
    }

    private boolean intaveOutgoing(Packet packet) {
        return packet instanceof C03PacketPlayer || packet instanceof C0FPacketConfirmTransaction || packet instanceof C00PacketKeepAlive || packet instanceof C0BPacketEntityAction;
    }

    private int getNullSlot() {
        int item = -1;
        int i = 36;
        while (i < 45) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && !(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock)) {
                item = i - 36;
            }
            ++i;
        }
        return item;
    }

    private boolean isInventory(C0FPacketConfirmTransaction packet) {
        return packet.uid > 0 && packet.uid < 100;
    }
}
