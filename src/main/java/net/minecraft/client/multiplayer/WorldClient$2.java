package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class WorldClient$2
implements Callable<String> {
    WorldClient$2() {
    }

    @Override
    public String call() {
        return String.valueOf(WorldClient.this.entitySpawnQueue.size()) + " total; " + WorldClient.this.entitySpawnQueue.toString();
    }
}
