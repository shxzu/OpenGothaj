package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$3
implements Callable<String> {
    Minecraft$3() {
    }

    @Override
    public String call() throws Exception {
        return Minecraft.this.currentScreen.getClass().getCanonicalName();
    }
}
