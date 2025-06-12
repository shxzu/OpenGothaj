package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;

class RenderGlobal$1
implements Callable<String> {
    private final double val$xCoord;
    private final double val$yCoord;
    private final double val$zCoord;

    RenderGlobal$1(double d, double d2, double d3) {
        this.val$xCoord = d;
        this.val$yCoord = d2;
        this.val$zCoord = d3;
    }

    @Override
    public String call() throws Exception {
        return CrashReportCategory.getCoordinateInfo(this.val$xCoord, this.val$yCoord, this.val$zCoord);
    }
}
