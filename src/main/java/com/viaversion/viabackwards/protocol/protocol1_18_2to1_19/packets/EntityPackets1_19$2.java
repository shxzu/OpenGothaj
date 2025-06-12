package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_19$2
extends PacketHandlers {
    EntityPackets1_19$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.handler(wrapper -> {
            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                wrapper.read(Type.NAMED_COMPOUND_TAG);
            }
        });
    }
}
