package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPacketRewriter1_20_2$2
extends PacketHandlers {
    BlockItemPacketRewriter1_20_2$2() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.SHORT);
        this.handler(wrapper -> wrapper.write(Type.ITEM1_13_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2))));
    }
}
