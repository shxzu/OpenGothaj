package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import net.minecraft.network.Packet;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventNoSlow;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(category=Category.MOVEMENT, description="Allows you to use items while running", name="No Slow", key=0)
@BCompiler(aot=BCompiler.AOT.AGGRESSIVE)
public class NoSlowModule
extends Mod {
    public ArrayList<Packet> packets = new ArrayList();
    public ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Hypixel", "Intave new", "Intave spoof", "AAC5", "AAC4", "Switch"});
    public boolean attack;
    public boolean swing;
    public Timer timer = new Timer();

    public NoSlowModule() {
        this.addSettings(this.mode);
    }

    @EventListener
    public void onNoSlow(EventNoSlow e) {
        e.setCancelled(true);
    }

    /*
     * Exception decompiling
     */
    @EventListener
    public void onMotion(EventMotion e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[9] lbl107 : CaseStatement: default:
, @NONE, blocks:[9] lbl107 : CaseStatement: default:
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

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        String string = this.mode.getMode().toLowerCase();
        string.hashCode();
    }
}
