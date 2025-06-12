package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_12$7
extends PacketHandlers {
    BlockItemPackets1_12$7() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            if (wrapper.get(Type.VAR_INT, 0) == 2) {
                wrapper.cancel();
            }
        });
    }
}
