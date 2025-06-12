package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_17$2
extends PacketHandlers {
    EntityPackets1_17$2() {
    }

    @Override
    public void register() {
        this.map(Type.NAMED_COMPOUND_TAG);
        this.map(Type.STRING);
        this.handler(EntityPackets1_17.this.worldDataTrackerHandler(0));
        this.handler(wrapper -> EntityPackets1_17.this.reduceExtendedHeight(wrapper.get(Type.NAMED_COMPOUND_TAG, 0), true));
    }
}
