package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;

class EntityPackets1_13_1$3
extends PacketHandlers {
    EntityPackets1_13_1$3() {
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
        this.map(Types1_13.METADATA_LIST);
        this.handler(EntityPackets1_13_1.this.getTrackerAndMetaHandler(Types1_13.METADATA_LIST, EntityTypes1_13.EntityType.PLAYER));
    }
}
