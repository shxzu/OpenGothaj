package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import java.util.LinkedList;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(category=Category.COMBAT, description="Uses blink to give you reach", name="Lag Range", key=0)
public class LagRangeModule
extends Mod {
    public LinkedList<Packet> outPackets = new LinkedList();
    public Timer timer = new Timer();
    public KillAuraModule killAura;
    public NumberSettings pulseDelay = new NumberSettings("pulse Delay", 200.0, 10.0, 500.0, 10.0);

    public LagRangeModule() {
        this.addSettings(this.pulseDelay);
    }

    @Override
    public void onEnable() {
        this.killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
    }

    @Override
    public void onDisable() {
        this.fullRelease();
    }

    @EventListener
    public void onWorldChange(EventWorldChange e) {
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onMotion(EventMotion e) {
        this.setInfo(String.valueOf(this.pulseDelay.getValue()));
        if (e.getType() != EventType.PRE) {
            return;
        }
        if (this.timer.hasTimeElapsed(this.pulseDelay.getValue(), false)) {
            this.fullRelease();
        }
        if (this.mc.thePlayer.hurtTime > 0) {
            this.fullRelease();
        }
    }

    @EventListener
    @BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
    public void onHit(EventHit e) {
        if (this.isHurtTime()) {
            e.setForced(true);
            this.fullRelease();
        }
    }

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        if (this.shouldCancel() && !e.isCancelled()) {
            e.setCancelled(true);
            this.outPackets.add(e.getPacket());
        }
    }

    /*
     * Exception decompiling
     */
    public void smartRelease() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
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
    public void fullRelease() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
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

    public boolean shouldCancel() {
        if (!this.killAura.isEnabled() || Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            this.fullRelease();
            return false;
        }
        return true;
    }

    public boolean isTargetCloseOrVisible() {
        Entity rayTracedEntity = RotationUtils.rayTrace(3.0, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
        if (this.killAura.target == null) {
            return false;
        }
        return rayTracedEntity == this.killAura.target || this.mc.objectMouseOver.entityHit == this.killAura.target || EntityUtils.getDistanceToEntityBox(this.killAura.target) <= 3.0;
    }

    public boolean isHurtTime() {
        return this.killAura.target.hurtTime <= 2;
    }
}
