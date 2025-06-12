package net.minecraft.client.main;

import net.minecraft.client.Minecraft;

class Main$2
extends Thread {
    Main$2(String $anonymous0) {
        super($anonymous0);
    }

    @Override
    public void run() {
        Minecraft.stopIntegratedServer();
    }
}
