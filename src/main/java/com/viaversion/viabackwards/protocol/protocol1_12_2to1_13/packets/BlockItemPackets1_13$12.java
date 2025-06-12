package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$12
extends PacketHandlers {
    BlockItemPackets1_13$12() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.VAR_INT);
        this.map(Type.ITEM1_8, Type.ITEM1_13);
        this.handler(BlockItemPackets1_13.this.itemToServerHandler(Type.ITEM1_13));
    }
}
