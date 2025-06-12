package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventGround;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMove;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.player.DisablerModule;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to fly", name="Flight", key=0)
public class FlightModule
extends Mod {
    private ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Verus", "Vulcan Jump", "BlocksMC", "Intave Boat", "Grim", "Mush mc"});
    public NumberSettings speed = new NumberSettings("Speed", 0.5, 0.1, 10.0, 0.01);
    public Timer timer = new Timer();
    public int flyTicks;
    public int posY;
    public double startY;
    public boolean started;
    public boolean done;
    public BlockPos blockPos;
    public EnumFacing facing;
    private List<Packet> inPackets = new ArrayList<Packet>();
    private List<Packet> outPackets = new ArrayList<Packet>();

    public FlightModule() {
        this.addSettings(this.mode, this.speed);
    }

    @Override
    public void onEnable() {
        this.startY = this.mc.thePlayer.posY;
        this.flyTicks = 0;
        this.started = false;
        this.done = false;
        this.timer.reset();
        switch (this.mode.getMode().toLowerCase()) {
            case "vulcan jump": {
                if (this.mc.thePlayer.onGround) break;
                this.toggle();
                return;
            }
        }
        this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindSneak.getKeyCode());
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.isDead = false;
        this.mc.thePlayer.speedInAir = 0.02f;
        this.mc.timer.timerSpeed = 1.0f;
        switch (this.mode.getMode().toLowerCase()) {
            case "verus": {
                MovementUtils.strafe(0.0f);
                break;
            }
            case "intave boat": {
                int i = 0;
                while (i < 3) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX - Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw)) * 8.0, this.mc.thePlayer.posY + 0.1, this.mc.thePlayer.posZ + Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw)) * 8.0, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                    ++i;
                }
                break;
            }
        }
        if (this.outPackets.size() > 0) {
            for (Packet p : this.outPackets) {
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
            }
            this.outPackets.clear();
        }
        this.mc.gameSettings.keyBindSneak.pressed = false;
        this.mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown((int)this.mc.gameSettings.keyBindForward.getKeyCode());
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onGameLoop(EventGameLoop e) {
        String string = this.mode.getMode().toLowerCase();
        string.hashCode();
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onGround(EventGround e) {
        switch (this.mode.getMode().toLowerCase()) {
            case "mush mc": {
                if (!(this.mc.thePlayer.posY <= this.startY)) break;
                e.setOnGround(true);
                break;
            }
            case "grim": {
                if (InventoryUtils.getBlockSlot(false) == -1) {
                    return;
                }
                if (!(this.mc.thePlayer.posY <= this.startY)) break;
                e.setOnGround(true);
            }
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        if (e.getType() != EventType.PRE) {
            return;
        }
        this.setInfo(this.mode.getMode());
        switch (this.mode.getMode().toLowerCase()) {
            case "mush mc": {
                if (this.mc.thePlayer.hurtTime == 0) {
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.0001, this.mc.thePlayer.posZ, false));
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                    this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
                    break;
                }
                if (this.mc.gameSettings.keyBindSneak.pressed) {
                    return;
                }
                if (this.mc.thePlayer.posY <= this.startY) {
                    this.mc.thePlayer.jump();
                    e.setOnGround(true);
                    return;
                }
                if (!this.mc.gameSettings.keyBindJump.pressed) break;
                this.mc.thePlayer.jump();
                e.setOnGround(true);
                break;
            }
            case "grim": {
                int slot = InventoryUtils.getBlockSlot(false);
                if (slot == -1) {
                    return;
                }
                if (!(this.mc.thePlayer.posY <= this.startY)) break;
                this.mc.thePlayer.jump();
                e.setOnGround(true);
                e.setPitch(90.0f);
                BlockPos hitPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
                EnumFacing facing = EnumFacing.DOWN;
                Vec3 hitVec = this.mc.objectMouseOver.hitVec;
                float f = (float)(hitVec.xCoord - (double)hitPos.getX());
                float f1 = (float)(hitVec.yCoord - (double)hitPos.getY());
                float f2 = (float)(hitVec.zCoord - (double)hitPos.getZ());
                int oldSlot = this.mc.thePlayer.inventory.currentItem;
                this.mc.thePlayer.inventory.currentItem = slot;
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getHeldItem()));
                this.mc.thePlayer.inventory.currentItem = oldSlot;
                break;
            }
            case "vanilla": {
                this.mc.thePlayer.motionY = this.mc.gameSettings.keyBindJump.pressed ? this.speed.getValue() / 2.0 : (this.mc.gameSettings.keyBindSneak.pressed ? -(this.speed.getValue() / 2.0) : 0.0);
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.motionX = 0.0;
                if (!MovementUtils.isMoving()) break;
                MovementUtils.strafe((float)this.speed.getValue());
                break;
            }
            case "blocksmc": {
                this.mc.thePlayer.motionY = 0.0;
                if (!MovementUtils.isMoving()) break;
                MovementUtils.strafe(0.55f);
                break;
            }
            case "verus": {
                if (this.mc.thePlayer.hurtTime > 0) {
                    this.done = true;
                }
                if (!this.done) break;
                this.mc.timer.timerSpeed = 1.0f;
                this.mc.gameSettings.keyBindForward.pressed = true;
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.5, this.mc.thePlayer.posZ), 1, new ItemStack(Blocks.stone.getItem(this.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                MovementUtils.strafe((float)(this.timer.getTime() <= (long)(Client.INSTANCE.getModuleManager().getModule(DisablerModule.class).isEnabled() ? 10000 : 1000) ? this.speed.getValue() : MovementUtils.getBaseMoveSpeed() * (double)1.3f));
                if (this.timer.getTime() <= 250L) {
                    MovementUtils.strafe(0.0f);
                }
                if (this.mc.thePlayer.fallDistance > 0.0f) {
                    this.mc.thePlayer.motionY = 0.0;
                }
                if (this.mc.gameSettings.keyBindJump.pressed) {
                    this.mc.thePlayer.motionY = 0.2;
                    break;
                }
                if (!this.mc.gameSettings.keyBindSneak.pressed) break;
                this.mc.thePlayer.motionY = -0.2;
                break;
            }
            case "vulcan jump": {
                if (this.flyTicks <= 5) {
                    this.mc.thePlayer.motionX = 0.0;
                    this.mc.thePlayer.motionY = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                    if (this.done) {
                        if (this.flyTicks == 0) {
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 1) {
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 2) {
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 3) {
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 4) {
                            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.0, this.mc.thePlayer.posZ);
                        }
                    }
                    ++this.flyTicks;
                } else {
                    if (this.mc.thePlayer.fallDistance > 2.5f) {
                        e.setOnGround(true);
                        this.mc.thePlayer.fallDistance = 0.0f;
                    }
                    if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                        this.mc.thePlayer.motionY = -0.1;
                    }
                    if (this.mc.thePlayer.onGround) {
                        this.toggle();
                    }
                }
                if (this.done || !this.mc.thePlayer.onGround) break;
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                e.setY(e.getY() - 0.1);
                this.done = true;
                this.flyTicks = 0;
            }
        }
    }

    /*
     * Exception decompiling
     */
    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMove(EventMove e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[5] lbl26 : CaseStatement: default:
, @NONE, blocks:[5] lbl26 : CaseStatement: default:
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
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMoveButton(EventMoveButton e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[5] lbl25 : CaseStatement: default:
, @NONE, blocks:[5] lbl25 : CaseStatement: default:
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
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onSendPacket(EventSendPacket e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[6] lbl68 : CaseStatement: default:
, @NONE, blocks:[6] lbl68 : CaseStatement: default:
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
     * Unable to fully structure code
     */
    @EventListener
    public void onReceivePacket(EventReceivePacket e) {
        var2_2 = this.mode.getMode().toLowerCase();
        tmp = -1;
        switch (var2_2.hashCode()) {
            case -925584629: {
                if (var2_2.equals("vulcan jump")) {
                    tmp = 1;
                }
                break;
            }
            case 112097665: {
                if (var2_2.equals("verus")) {
                    tmp = 1;
                }
                break;
            }
            case 233102203: {
                if (!var2_2.equals("vanilla")) break;
                tmp = 1;
                break;
            }
        }
        ** switch (tmp)
lbl21:
        // 2 sources

    }

    public void processBlockData() {
        this.posY = (int)(this.mc.thePlayer.posY - 1.0);
        BlockPos position = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
        this.blockPos = this.getBlockPos(this.mc.thePlayer.posX, this.posY, this.mc.thePlayer.posZ);
        if (this.blockPos != null) {
            this.facing = this.getPlaceSide(this.mc.thePlayer.posX, this.posY, this.mc.thePlayer.posZ);
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
                    if (FlightModule.isValidBock(new BlockPos(x, y, z))) {
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
        if (!(FlightModule.isPosSolid(this.blockPos.add(0, 1, 0)) || this.blockPos.add(0, 1, 0).equals(playerPos) || this.mc.thePlayer.onGround)) {
            BlockPos pos = new BlockPos(posX, posY, posZ);
            BlockPos bp2 = this.blockPos.add(0, 1, 0);
            Vec3 vec42 = this.getBestHitFeet(bp2);
            positions.add(vec42);
            hashMap.put(vec42, EnumFacing.UP);
        }
        if (!FlightModule.isPosSolid(this.blockPos.add(1, 0, 0)) && !this.blockPos.add(1, 0, 0).equals(playerPos)) {
            bp = this.blockPos.add(1, 0, 0);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.EAST);
        }
        if (!FlightModule.isPosSolid(this.blockPos.add(-1, 0, 0)) && !this.blockPos.add(-1, 0, 0).equals(playerPos)) {
            bp = this.blockPos.add(-1, 0, 0);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.WEST);
        }
        if (!FlightModule.isPosSolid(this.blockPos.add(0, 0, 1)) && !this.blockPos.add(0, 0, 1).equals(playerPos)) {
            bp = this.blockPos.add(0, 0, 1);
            vec4 = this.getBestHitFeet(bp);
            positions.add(vec4);
            hashMap.put(vec4, EnumFacing.SOUTH);
        }
        if (!FlightModule.isPosSolid(this.blockPos.add(0, 0, -1)) && !this.blockPos.add(0, 0, -1).equals(playerPos)) {
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

    public static boolean isPosSolid(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }

    public static boolean isValidBock(BlockPos blockPos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
    }

    private double getBestBlock(Vec3 vec3) {
        return this.mc.thePlayer.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
}
