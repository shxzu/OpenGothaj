package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$9
extends PacketHandlers {
    BlockItemPackets1_13$9() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.ITEM1_13, Type.ITEM1_8);
        this.handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM1_8));
    }
}
