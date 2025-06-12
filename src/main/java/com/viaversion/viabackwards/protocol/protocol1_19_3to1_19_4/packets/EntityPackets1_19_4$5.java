package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_19_4$5
extends PacketHandlers {
    EntityPackets1_19_4$5() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.STRING);
        this.handler(EntityPackets1_19_4.this.worldDataTrackerHandlerByKey());
    }
}
