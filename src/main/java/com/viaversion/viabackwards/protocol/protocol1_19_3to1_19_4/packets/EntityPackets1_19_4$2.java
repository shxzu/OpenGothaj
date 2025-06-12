package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_19_4$2
extends PacketHandlers {
    EntityPackets1_19_4$2() {
    }

    @Override
    protected void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.create(Type.BOOLEAN, false);
    }
}
