package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;

class EntityPackets1_12$3
extends PacketHandlers {
    EntityPackets1_12$3() {
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
        this.map(Types1_12.METADATA_LIST);
        this.handler(EntityPackets1_12.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, EntityTypes1_12.EntityType.PLAYER));
    }
}
