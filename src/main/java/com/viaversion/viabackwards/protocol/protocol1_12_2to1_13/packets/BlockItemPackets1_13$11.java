package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$11
extends PacketHandlers {
    BlockItemPackets1_13$11() {
    }

    @Override
    public void register() {
        this.map(Type.SHORT);
        this.map(Type.ITEM1_8, Type.ITEM1_13);
        this.handler(BlockItemPackets1_13.this.itemToServerHandler(Type.ITEM1_13));
    }
}
