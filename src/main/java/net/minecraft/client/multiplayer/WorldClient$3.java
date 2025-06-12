package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;
import net.minecraft.client.multiplayer.WorldClient;

class WorldClient$3
implements Callable<String> {
    WorldClient$3() {
    }

    @Override
    public String call() throws Exception {
        return ((WorldClient)WorldClient.this).mc.thePlayer.getClientBrand();
    }
}
