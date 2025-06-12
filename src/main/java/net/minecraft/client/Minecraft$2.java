package net.minecraft.client;

class Minecraft$2
extends Thread {
    Minecraft$2(String $anonymous0) {
        super($anonymous0);
    }

    @Override
    public void run() {
        while (Minecraft.this.running) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }
}
