package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPacketRewriter1_20_2$1
extends PacketHandlers {
    BlockItemPacketRewriter1_20_2$1() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            Item[] items;
            for (Item item : items = wrapper.read(Type.ITEM1_20_2_ARRAY)) {
                BlockItemPacketRewriter1_20_2.this.handleItemToClient(item);
            }
            wrapper.write(Type.ITEM1_13_2_ARRAY, items);
            wrapper.write(Type.ITEM1_13_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
        });
    }
}
