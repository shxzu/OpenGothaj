package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viaversion.libs.gson.JsonElement;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

final class EntityPackets1_19_3$PlayerProfileUpdate {
    private final UUID uuid;
    private final int gamemode;
    private final int latency;
    private final JsonElement displayName;

    private EntityPackets1_19_3$PlayerProfileUpdate(UUID uuid, int gamemode, int latency, @Nullable JsonElement displayName) {
        this.uuid = uuid;
        this.gamemode = gamemode;
        this.latency = latency;
        this.displayName = displayName;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public int gamemode() {
        return this.gamemode;
    }

    public int latency() {
        return this.latency;
    }

    public @Nullable JsonElement displayName() {
        return this.displayName;
    }
}
