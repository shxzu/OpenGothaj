package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14$2
extends PacketHandlers {
    EntityPackets1_14$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.handler(wrapper -> {
            double x = (double)wrapper.get(Type.SHORT, 0).shortValue() / 4096.0;
            double y = (double)wrapper.get(Type.SHORT, 1).shortValue() / 4096.0;
            double z = (double)wrapper.get(Type.SHORT, 2).shortValue() / 4096.0;
            EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, x, y, z, false, true);
        });
    }
}
