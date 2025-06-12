package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_12$4
extends PacketHandlers {
    BlockItemPackets1_12$4() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int idx = wrapper.get(Type.VAR_INT, 0);
            wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_12.this.handleBlockID(idx));
        });
    }
}
