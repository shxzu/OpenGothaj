package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$12
implements Callable<String> {
    Minecraft$12() {
    }

    @Override
    public String call() throws Exception {
        StringBuilder stringbuilder = new StringBuilder();
        for (String s : Minecraft.this.gameSettings.resourcePacks) {
            if (stringbuilder.length() > 0) {
                stringbuilder.append(", ");
            }
            stringbuilder.append(s);
            if (!Minecraft.this.gameSettings.incompatibleResourcePacks.contains(s)) continue;
            stringbuilder.append(" (incompatible)");
        }
        return stringbuilder.toString();
    }
}
