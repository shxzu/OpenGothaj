package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19$4
extends PacketHandlers {
    BlockItemPackets1_19$4() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.POSITION1_14);
        this.map(Type.UNSIGNED_BYTE);
        this.create(Type.VAR_INT, 0);
    }
}
