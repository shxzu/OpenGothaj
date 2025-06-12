package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.OpenGlHelper;

class Minecraft$8
implements Callable<String> {
    Minecraft$8() {
    }

    @Override
    public String call() {
        return OpenGlHelper.getLogText();
    }
}
