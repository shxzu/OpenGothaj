package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import java.util.ArrayList;

class EntityPackets1_15$2
extends PacketHandlers {
    EntityPackets1_15$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.handler(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList()));
        this.handler(wrapper -> {
            int type = wrapper.get(Type.VAR_INT, 1);
            EntityType entityType = EntityTypes1_15.getTypeFromId(type);
            EntityPackets1_15.this.tracker(wrapper.user()).addEntity(wrapper.get(Type.VAR_INT, 0), entityType);
            wrapper.set(Type.VAR_INT, 1, EntityTypeMapping.getOldEntityId(type));
        });
    }
}
