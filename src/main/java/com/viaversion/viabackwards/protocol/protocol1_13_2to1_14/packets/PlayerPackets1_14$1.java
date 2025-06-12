package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_14$1
extends PacketHandlers {
    PlayerPackets1_14$1() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.read(Type.BOOLEAN);
        this.handler(wrapper -> {
            byte difficulty = wrapper.get(Type.UNSIGNED_BYTE, 0).byteValue();
            wrapper.user().get(DifficultyStorage.class).setDifficulty(difficulty);
        });
    }
}
