package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityTypeMapping;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;

class EntityPackets1_13$3
extends PacketHandlers {
    EntityPackets1_13$3() {
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
        this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
        this.handler(wrapper -> {
            int type = wrapper.get(Type.VAR_INT, 1);
            EntityTypes1_13.EntityType entityType = EntityTypes1_13.getTypeFromId(type, false);
            EntityPackets1_13.this.tracker(wrapper.user()).addEntity(wrapper.get(Type.VAR_INT, 0), entityType);
            int oldId = EntityTypeMapping.getOldId(type);
            if (oldId == -1) {
                if (!EntityPackets1_13.this.hasData(entityType)) {
                    ViaBackwards.getPlatform().getLogger().warning("Could not find 1.12 entity type for 1.13 entity type " + type + "/" + entityType);
                }
            } else {
                wrapper.set(Type.VAR_INT, 1, oldId);
            }
        });
        this.handler(EntityPackets1_13.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
    }
}
