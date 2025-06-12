package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_15$3
extends PacketHandlers {
    EntityPackets1_15$3() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.read(Type.LONG);
    }
}
