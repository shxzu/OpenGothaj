package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_19_4$4
extends PacketHandlers {
    EntityPackets1_19_4$4() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.read(Type.FLOAT);
        this.create(Type.UNSIGNED_BYTE, (short)1);
    }
}
