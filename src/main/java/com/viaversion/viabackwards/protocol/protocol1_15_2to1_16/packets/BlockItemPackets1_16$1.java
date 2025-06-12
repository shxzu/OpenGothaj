package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_16$1
extends PacketHandlers {
    BlockItemPackets1_16$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.read(Type.BOOLEAN);
    }
}
