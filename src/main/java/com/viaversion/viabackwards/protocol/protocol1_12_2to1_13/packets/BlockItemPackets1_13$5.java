package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$5
extends PacketHandlers {
    BlockItemPackets1_13$5() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.ITEM1_13_ARRAY, Type.ITEM1_8_SHORT_ARRAY);
        this.handler(BlockItemPackets1_13.this.itemArrayToClientHandler(Type.ITEM1_8_SHORT_ARRAY));
    }
}
