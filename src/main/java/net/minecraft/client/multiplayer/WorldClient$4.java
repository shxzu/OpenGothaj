package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class WorldClient$4
implements Callable<String> {
    WorldClient$4() {
    }

    @Override
    public String call() throws Exception {
        return WorldClient.this.mc.getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
    }
}
