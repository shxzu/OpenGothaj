package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_13$6
extends PacketHandlers {
    EntityPackets1_13$6() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(EntityPackets1_13.this.getDimensionHandler(0));
        this.handler(wrapper -> wrapper.user().get(BackwardsBlockStorage.class).clear());
    }
}
