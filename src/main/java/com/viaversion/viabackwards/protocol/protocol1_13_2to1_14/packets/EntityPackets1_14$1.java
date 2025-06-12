package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14$1
extends PacketHandlers {
    EntityPackets1_14$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.handler(wrapper -> EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, false, false));
    }
}
