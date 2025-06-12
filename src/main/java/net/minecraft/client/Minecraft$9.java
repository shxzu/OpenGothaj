package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$9
implements Callable<String> {
    Minecraft$9() {
    }

    @Override
    public String call() {
        return Minecraft.this.gameSettings.useVbo ? "Yes" : "No";
    }
}
