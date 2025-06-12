package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_17$4
extends PacketHandlers {
    EntityPackets1_17$4() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> wrapper.write(Type.INT, wrapper.read(Type.VAR_INT)));
    }
}
