package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;

class EntityPackets1_14$8
extends PacketHandlers {
    EntityPackets1_14$8() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
        this.handler(EntityPackets1_14.this.getTrackerAndMetaHandler(Types1_13_2.METADATA_LIST, EntityTypes1_14.PLAYER));
        this.handler(wrapper -> EntityPackets1_14.this.positionHandler.cacheEntityPosition(wrapper, true, false));
    }
}
