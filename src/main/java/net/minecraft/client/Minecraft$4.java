package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$4
implements Callable<String> {
    Minecraft$4() {
    }

    @Override
    public String call() throws Exception {
        return Minecraft.this.currentScreen.getClass().getCanonicalName();
    }
}
