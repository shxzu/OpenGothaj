package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19$6
extends PacketHandlers {
    BlockItemPackets1_19$6() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.create(Type.VAR_INT, 0);
    }
}
