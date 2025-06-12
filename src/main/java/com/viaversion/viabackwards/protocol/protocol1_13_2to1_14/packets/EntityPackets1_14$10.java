package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14$10
extends PacketHandlers {
    EntityPackets1_14$10() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(wrapper -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            int dimensionId = wrapper.get(Type.INT, 0);
            clientWorld.setEnvironment(dimensionId);
            short difficulty = wrapper.user().get(DifficultyStorage.class).getDifficulty();
            wrapper.write(Type.UNSIGNED_BYTE, difficulty);
            wrapper.user().get(ChunkLightStorage.class).clear();
        });
    }
}
