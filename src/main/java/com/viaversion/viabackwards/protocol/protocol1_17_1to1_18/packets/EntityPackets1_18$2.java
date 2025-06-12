package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_18$2
extends PacketHandlers {
    EntityPackets1_18$2() {
    }

    @Override
    public void register() {
        this.map(Type.NAMED_COMPOUND_TAG);
        this.map(Type.STRING);
        this.handler(EntityPackets1_18.this.worldDataTrackerHandler(0));
    }
}
