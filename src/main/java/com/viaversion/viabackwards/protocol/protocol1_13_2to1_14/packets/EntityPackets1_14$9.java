package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14$9
extends PacketHandlers {
    EntityPackets1_14$9() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.INT);
        this.handler(EntityPackets1_14.this.getTrackerHandler(EntityTypes1_14.PLAYER, Type.INT));
        this.handler(EntityPackets1_14.this.getDimensionHandler(1));
        this.handler(wrapper -> {
            short difficulty = wrapper.user().get(DifficultyStorage.class).getDifficulty();
            wrapper.write(Type.UNSIGNED_BYTE, difficulty);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.STRING);
            wrapper.read(Type.VAR_INT);
        });
    }
}
