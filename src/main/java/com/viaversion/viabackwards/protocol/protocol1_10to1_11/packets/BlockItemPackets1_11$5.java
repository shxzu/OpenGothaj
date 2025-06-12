package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_11$5
extends PacketHandlers {
    BlockItemPackets1_11$5() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int idx = wrapper.get(Type.VAR_INT, 0);
            wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_11.this.handleBlockID(idx));
        });
    }
}
