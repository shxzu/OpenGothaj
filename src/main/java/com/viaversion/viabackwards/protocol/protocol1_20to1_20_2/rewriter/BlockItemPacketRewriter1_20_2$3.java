package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPacketRewriter1_20_2$3
extends PacketHandlers {
    BlockItemPacketRewriter1_20_2$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            byte slot;
            do {
                slot = wrapper.passthrough(Type.BYTE);
                wrapper.write(Type.ITEM1_13_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
            } while ((slot & 0xFFFFFF80) != 0);
        });
    }
}
