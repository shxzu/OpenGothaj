package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;

class EntityPackets1_13$4
extends PacketHandlers {
    EntityPackets1_13$4() {
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
        this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
        this.handler(EntityPackets1_13.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, EntityTypes1_13.EntityType.PLAYER));
    }
}
