package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import java.util.LinkedList;
import net.minecraft.network.Packet;
import net.minecraft.potion.Potion;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to walk faster", name="Speed", key=0)
public class SpeedModule
extends Mod {
    public KillAuraModule ka;
    public int groundTicks;
    public int velocityTicks;
    public int ticks;
    public int transTicks;
    public boolean forward;
    public boolean backward;
    public boolean left;
    public boolean right;
    public boolean shouldStrafe;
    public boolean started;
    public boolean done;
    public Timer timer = new Timer();
    public LinkedList<Packet> outPackets = new LinkedList();
    public LinkedList<Packet> inPackets = new LinkedList();
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Vulcan", "Vulcan Low", "Verus", "NCP", "Hypixel", "Intave", "Matrix", "Legit", "BlocksMC"});
    public NumberSettings speed = new NumberSettings("Speed", 0.5, 0.1, 3.0, 0.01);
    public BooleanSettings intaveJump = new BooleanSettings("Jump", false);
    public NumberSettings intaveStrafeTicks = new NumberSettings("Intave Strafe Delay", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 3.0, 1.0, 15.0, 1.0);
    public NumberSettings intaveOffset = new NumberSettings("Intave Offset", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 0.02, 0.0, 0.25, 0.01);
    public NumberSettings intaveMinSpeed = new NumberSettings("Intave Min Speed", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 0.02, 0.0, 0.25, 0.01);
    public NumberSettings timerTimer = new NumberSettings("Timer", 1.0, 1.0, 2.0, 0.001);
    public BooleanSettings intaveSmart = new BooleanSettings("Intave Smart", () -> this.mode.getMode().equalsIgnoreCase("Intave"), true);
    public BooleanSettings intaveSmartSafe = new BooleanSettings("Intave Smart Safe", () -> this.mode.getMode().equalsIgnoreCase("Intave"), true);
    public NumberSettings intaveSmartDelay = new NumberSettings("Intave Smart Delay", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 2.0, 0.0, 15.0, 1.0);
    public BooleanSettings vulcanFast = new BooleanSettings("Vulcan Fast", () -> this.mode.getMode().equalsIgnoreCase("Vulcan"), true);

    public SpeedModule() {
        this.addSettings(this.mode, this.speed, this.intaveJump, this.intaveStrafeTicks, this.intaveOffset, this.intaveMinSpeed, this.timerTimer, this.intaveSmart, this.intaveSmartSafe, this.intaveSmartDelay, this.vulcanFast);
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.speedInAir = 0.02f;
        this.mc.timer.timerSpeed = 1.0f;
        for (Packet p : this.outPackets) {
            if (p == null) continue;
            this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
        }
        this.outPackets.clear();
        for (Packet p : this.inPackets) {
            if (p == null) continue;
            p.processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
        }
        this.inPackets.clear();
        RotationUtils.customRots = false;
    }

    @Override
    public void onEnable() {
        switch (this.mode.getMode().toLowerCase()) {
            case "intave": {
                this.started = false;
            }
        }
        this.timer.reset();
        this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        this.setInfo(this.mode.getMode());
        switch (this.mode.getMode().toLowerCase()) {
            case "vanilla": {
                if (e.getType() != EventType.PRE) break;
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.motionX = 0.0;
                if (MovementUtils.isMoving()) {
                    MovementUtils.strafe((float)this.speed.getValue());
                }
                if (!this.mc.thePlayer.onGround || !MovementUtils.isMoving()) break;
                this.mc.thePlayer.jump();
                break;
            }
            case "legit": {
                if (e.getType() != EventType.PRE || !this.mc.thePlayer.onGround || !MovementUtils.isMoving()) break;
                this.mc.thePlayer.jump();
                break;
            }
            case "blocksmc": {
                if (e.getType() != EventType.PRE) break;
                if (!MovementUtils.isMoving()) {
                    this.groundTicks = 0;
                    return;
                }
                this.groundTicks = this.mc.thePlayer.onGround ? 0 : ++this.groundTicks;
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                }
                MovementUtils.strafe((float)(this.mc.gameSettings.keyBindForward.isPressed() ? (double)((float)MovementUtils.getBaseMoveSpeed()) * 1.2 : MovementUtils.getBaseMoveSpeed() * 0.9));
                break;
            }
            case "intave": {
                if (e.getType() != EventType.POST) break;
                if (!this.intaveJump.isEnabled() && !this.mc.thePlayer.onGround) {
                    return;
                }
                if (MovementUtils.isMoving()) {
                    float[] inputs = MovementUtils.silentStrafe(this.mc.thePlayer.movementInput.moveStrafe, this.mc.thePlayer.movementInput.moveForward, RotationUtils.serverYaw, true);
                    if (!RotationUtils.customRots) {
                        inputs[1] = this.mc.thePlayer.movementInput.moveForward;
                        inputs[0] = this.mc.thePlayer.movementInput.moveStrafe;
                    }
                    if (!this.ka.MoveFix.getMode().equalsIgnoreCase("Silent")) {
                        inputs[1] = this.mc.thePlayer.movementInput.moveForward;
                        inputs[0] = this.mc.thePlayer.movementInput.moveStrafe;
                    }
                    float intaveSpeed = (float)Math.max(this.intaveMinSpeed.getValue(), MovementUtils.getSpeed());
                    this.mc.thePlayer.isSprinting();
                    ++this.ticks;
                    if (this.velocityTicks < 10 && this.mc.thePlayer.hurtTime != 0 || this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) break;
                    if ((double)this.ticks > this.intaveStrafeTicks.getValue() && MovementUtils.getSpeed() <= MovementUtils.getBaseMoveSpeed() / (1.0 + this.intaveOffset.getValue())) {
                        MovementUtils.strafe(intaveSpeed, RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw, inputs[1], inputs[0]);
                        this.ticks = 0;
                        this.timer.reset();
                        this.shouldStrafe = false;
                    }
                    if (!this.shouldStrafe || !((double)this.ticks > this.intaveSmartDelay.getValue()) || !(MovementUtils.getSpeed() <= MovementUtils.getBaseMoveSpeed() / (1.0 + this.intaveOffset.getValue())) && this.intaveSmartSafe.isEnabled()) break;
                    MovementUtils.strafe(intaveSpeed, RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw, inputs[1], inputs[0]);
                    this.timer.reset();
                    this.ticks = 0;
                    this.shouldStrafe = false;
                    break;
                }
                this.mc.timer.timerSpeed = 1.0f;
                break;
            }
            case "verus": {
                if (MovementUtils.isMoving()) {
                    if (this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.jump();
                    }
                    MovementUtils.strafe((float)(this.mc.gameSettings.keyBindForward.pressed ? MovementUtils.getBaseMoveSpeed() * 1.3 : MovementUtils.getBaseMoveSpeed()));
                    break;
                }
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                break;
            }
            case "vulcan": {
                float speedModifier;
                if (e.getType() != EventType.PRE) break;
                this.groundTicks = this.mc.thePlayer.onGround ? 0 : ++this.groundTicks;
                float yaw = RotationUtils.customRots ? (this.ka.MoveFix.getMode().equalsIgnoreCase("Legit") ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw) : this.mc.thePlayer.rotationYaw;
                float f = speedModifier = this.vulcanFast.isEnabled() ? 0.0f : 0.05f;
                if (!MovementUtils.isMoving()) break;
                switch (this.groundTicks) {
                    case 0: {
                        if (!this.mc.gameSettings.keyBindJump.pressed) {
                            this.mc.thePlayer.jump();
                        }
                        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MovementUtils.strafe(0.6f - speedModifier, (float)MovementUtils.getDirection(yaw));
                            break;
                        }
                        MovementUtils.strafe(0.485f - speedModifier, (float)MovementUtils.getDirection(yaw));
                        break;
                    }
                    case 9: {
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                        break;
                    }
                    case 1: 
                    case 2: {
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                        break;
                    }
                    case 5: {
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                    }
                }
                break;
            }
            case "vulcan low": {
                float speedModifier;
                if (e.getType() != EventType.PRE) break;
                this.groundTicks = this.mc.thePlayer.onGround ? 0 : ++this.groundTicks;
                float yaw = RotationUtils.customRots ? (this.ka.MoveFix.getMode().equalsIgnoreCase("Legit") ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw) : this.mc.thePlayer.rotationYaw;
                float f = speedModifier = this.vulcanFast.isEnabled() ? 0.0f : 0.05f;
                if (!MovementUtils.isMoving()) break;
                switch (this.groundTicks) {
                    case 0: {
                        if (!this.mc.gameSettings.keyBindJump.pressed) {
                            this.mc.thePlayer.jump();
                        }
                        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MovementUtils.strafe(0.6f - speedModifier, (float)MovementUtils.getDirection(yaw));
                            break;
                        }
                        MovementUtils.strafe(0.485f - speedModifier, (float)MovementUtils.getDirection(yaw));
                        break;
                    }
                    case 9: {
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                        break;
                    }
                    case 1: 
                    case 2: {
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                        break;
                    }
                    case 5: {
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                    }
                }
                break;
            }
            case "ncp": {
                if (e.getType() != EventType.PRE) break;
                if (MovementUtils.isMoving()) {
                    if (this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.jump();
                    }
                    MovementUtils.strafe((float)Math.max(MovementUtils.getBaseMoveSpeed() / 1.1, MovementUtils.getSpeed()));
                    break;
                }
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                break;
            }
            case "hypixel": {
                if (e.getType() != EventType.PRE || !MovementUtils.isMoving()) break;
                if (this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.jump();
                    MovementUtils.strafe((float)Math.max(this.mc.thePlayer.isSprinting() ? MovementUtils.getBaseMoveSpeed() * 1.65 : MovementUtils.getBaseMoveSpeed() * 1.8, MovementUtils.getSpeed()));
                    break;
                }
                if (this.mc.thePlayer.hurtTime != 9) break;
                MovementUtils.strafe((float)Math.max(this.mc.thePlayer.isSprinting() ? MovementUtils.getBaseMoveSpeed() * 1.0 : MovementUtils.getBaseMoveSpeed() * 1.0, MovementUtils.getSpeed()));
                break;
            }
            case "matrix": {
                if (e.getType() != EventType.PRE) break;
                float speed = (float)Math.max(MovementUtils.getBaseMoveSpeed() / 1.25, MovementUtils.getSpeed());
                if (this.mc.thePlayer.isSprinting()) {
                    speed = (float)MovementUtils.getSpeed();
                }
                if (!MovementUtils.isMoving()) break;
                if (this.mc.thePlayer.onGround) {
                    MovementUtils.strafe(speed);
                    this.mc.thePlayer.jump();
                    this.shouldStrafe = true;
                }
                if (!(this.mc.thePlayer.fallDistance > 0.0f) || !this.shouldStrafe) break;
                this.shouldStrafe = false;
                MovementUtils.strafe(speed);
            }
        }
    }

    @EventListener
    public void onJump(EventJump e) {
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
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[8] lbl67 : CaseStatement: default:
, @NONE, blocks:[8] lbl67 : CaseStatement: default:
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
}
