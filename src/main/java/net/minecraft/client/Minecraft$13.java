package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$13
implements Callable<String> {
    Minecraft$13() {
    }

    @Override
    public String call() throws Exception {
        return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
    }
}
