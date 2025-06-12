package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.client.renderer.OpenGlHelper;

class Minecraft$15
implements Callable<String> {
    Minecraft$15() {
    }

    @Override
    public String call() {
        return OpenGlHelper.getCpu();
    }
}
