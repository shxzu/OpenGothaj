package com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4;

import com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.storage.PlayerHandStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_16_3To1_16_4$1
extends PacketHandlers {
    Protocol1_16_3To1_16_4$1() {
    }

    @Override
    public void register() {
        this.map(Type.ITEM1_13_2);
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            int slot = wrapper.read(Type.VAR_INT);
            if (slot == 1) {
                wrapper.write(Type.VAR_INT, 40);
            } else {
                wrapper.write(Type.VAR_INT, wrapper.user().get(PlayerHandStorage.class).getCurrentHand());
            }
        });
    }
}
