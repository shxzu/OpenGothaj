package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_19_3$2
extends PacketHandlers {
    EntityPackets1_19_3$2() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.handler(EntityPackets1_19_3.this.worldDataTrackerHandlerByKey());
        this.handler(wrapper -> {
            byte keepDataMask = wrapper.read(Type.BYTE);
            wrapper.write(Type.BOOLEAN, (keepDataMask & 1) != 0);
        });
    }
}
