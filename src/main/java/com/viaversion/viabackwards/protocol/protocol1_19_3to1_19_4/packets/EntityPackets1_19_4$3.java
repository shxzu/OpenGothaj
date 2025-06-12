package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_19_4$3
extends PacketHandlers {
    EntityPackets1_19_4$3() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.read(Type.VAR_INT);
        this.read(Type.VAR_INT);
        this.read(Type.VAR_INT);
        this.handler(wrapper -> {
            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                wrapper.read(Type.DOUBLE);
                wrapper.read(Type.DOUBLE);
                wrapper.read(Type.DOUBLE);
            }
        });
        this.create(Type.BYTE, (byte)2);
    }
}
