package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;

class EntityRenderer$4
implements Callable<String> {
    private final ScaledResolution val$scaledresolution;

    EntityRenderer$4(ScaledResolution scaledResolution) {
        this.val$scaledresolution = scaledResolution;
    }

    @Override
    public String call() throws Exception {
        return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", this.val$scaledresolution.getScaledWidth(), this.val$scaledresolution.getScaledHeight(), ((EntityRenderer)EntityRenderer.this).mc.displayWidth, ((EntityRenderer)EntityRenderer.this).mc.displayHeight, this.val$scaledresolution.getScaleFactor());
    }
}
