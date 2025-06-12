package net.minecraft.client;

class Minecraft$16
implements Runnable {
    Minecraft$16() {
    }

    @Override
    public void run() {
        Minecraft.this.refreshResources();
    }
}
