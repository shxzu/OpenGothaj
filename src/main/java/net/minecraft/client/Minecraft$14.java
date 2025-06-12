package net.minecraft.client;

import java.util.concurrent.Callable;

class Minecraft$14
implements Callable<String> {
    Minecraft$14() {
    }

    @Override
    public String call() throws Exception {
        return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
    }
}
