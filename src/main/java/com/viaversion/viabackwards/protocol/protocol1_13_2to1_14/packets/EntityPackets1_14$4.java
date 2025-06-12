package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;

class EntityPackets1_14$4
extends PacketHandlers {
    EntityPackets1_14$4() {
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
        this.map(Types1_14.METADATA_LIST, Types1_13_2.METADATA_LIST);
        this.handler(wrapper -> {
            int type = wrapper.get(Type.VAR_INT, 1);
            EntityType entityType = EntityTypes1_14.getTypeFromId(type);
            EntityPackets1_14.this.addTrackedEntity(wrapper, wrapper.get(Type.VAR_INT, 0), entityType);
            int oldId = EntityPackets1_14.this.newEntityId(type);
            if (oldId == -1) {
                EntityData entityData = EntityPackets1_14.this.entityDataForType(entityType);
                if (entityData == null) {
                    ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13.2 entity type for 1.14 entity type " + type + "/" + entityType);
                    wrapper.cancel();
                } else {
                    wrapper.set(Type.VAR_INT, 1, entityData.replacementId());
                }
            } else {
                wrapper.set(Type.VAR_INT, 1, oldId);
            }
        });
        this.handler(EntityPackets1_14.this.getMobSpawnRewriter(Types1_13_2.METADATA_LIST));
    }
}
