package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPacketRewriter1_20_2$4
extends PacketHandlers {
    BlockItemPacketRewriter1_20_2$4() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int length = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < length; ++i) {
                wrapper.passthrough(Type.SHORT);
                wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.ITEM1_13_2)));
            }
            wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.ITEM1_13_2)));
        });
    }
}
