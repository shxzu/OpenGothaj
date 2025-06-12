package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_15$1
extends PacketHandlers {
    EntityPackets1_15$1() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.FLOAT);
        this.handler(wrapper -> {
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                wrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(wrapper.get(Type.FLOAT, 0).floatValue() == 1.0f);
            }
        });
    }
}
