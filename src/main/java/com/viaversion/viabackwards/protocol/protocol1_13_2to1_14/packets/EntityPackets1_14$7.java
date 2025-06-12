package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14$7
extends PacketHandlers {
    EntityPackets1_14$7() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.map(Type.POSITION1_14, Type.POSITION1_8);
        this.map(Type.BYTE);
        this.handler(wrapper -> EntityPackets1_14.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), EntityTypes1_14.PAINTING));
    }
}
