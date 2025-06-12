package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_11_1$1
extends PacketHandlers {
    EntityPackets1_11_1$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.BYTE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.handler(EntityPackets1_11_1.this.getObjectTrackerHandler());
        this.handler(EntityPackets1_11_1.this.getObjectRewriter(id -> EntityTypes1_11.ObjectType.findById(id.byteValue()).orElse(null)));
    }
}
