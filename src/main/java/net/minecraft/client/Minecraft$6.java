package net.minecraft.client;

import java.util.concurrent.Callable;
import org.lwjgl.Sys;

class Minecraft$6
implements Callable<String> {
    Minecraft$6() {
    }

    @Override
    public String call() {
        return Sys.getVersion();
    }
}
