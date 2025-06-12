package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_10$2
extends PacketHandlers {
    BlockItemPackets1_10$2() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int idx = wrapper.get(Type.VAR_INT, 0);
            wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_10.this.handleBlockID(idx));
        });
    }
}
