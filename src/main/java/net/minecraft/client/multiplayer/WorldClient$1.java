package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class WorldClient$1
implements Callable<String> {
    WorldClient$1() {
    }

    @Override
    public String call() {
        return String.valueOf(WorldClient.this.entityList.size()) + " total; " + WorldClient.this.entityList.toString();
    }
}
