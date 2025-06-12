package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_15$4
extends PacketHandlers {
    EntityPackets1_15$4() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.INT);
        this.read(Type.LONG);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.handler(EntityPackets1_15.this.getTrackerHandler(EntityTypes1_15.PLAYER, Type.INT));
        this.handler(wrapper -> {
            boolean immediateRespawn = wrapper.read(Type.BOOLEAN) == false;
            wrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(immediateRespawn);
        });
    }
}
