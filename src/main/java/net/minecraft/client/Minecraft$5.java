package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$5
implements Callable<String> {
    Minecraft$5() {
    }

    @Override
    public String call() throws Exception {
        return Minecraft.this.launchedVersion;
    }
}
