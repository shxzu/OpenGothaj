package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_17$3
extends PacketHandlers {
    EntityPackets1_17$3() {
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> wrapper.read(Type.BOOLEAN));
    }
}
