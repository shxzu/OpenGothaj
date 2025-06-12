package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_14$8
extends PacketHandlers {
    BlockItemPackets1_14$8() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.read(Type.BOOLEAN);
    }
}
