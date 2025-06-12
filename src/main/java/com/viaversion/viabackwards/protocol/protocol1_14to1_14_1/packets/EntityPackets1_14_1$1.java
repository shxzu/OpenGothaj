package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14_1$1
extends PacketHandlers {
    EntityPackets1_14_1$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.handler(EntityPackets1_14_1.this.getTrackerHandler());
    }
}
