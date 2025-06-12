package net.minecraft.client.renderer;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.EntityRenderer;

class EntityRenderer$2
implements Callable<String> {
    EntityRenderer$2() {
    }

    @Override
    public String call() throws Exception {
        return ((EntityRenderer)EntityRenderer.this).mc.currentScreen.getClass().getCanonicalName();
    }
}
