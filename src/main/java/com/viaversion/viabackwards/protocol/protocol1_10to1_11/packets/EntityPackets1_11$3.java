package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import java.util.List;

class EntityPackets1_11$3
extends PacketHandlers {
    EntityPackets1_11$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map((Type)Type.VAR_INT, Type.UNSIGNED_BYTE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Types1_9.METADATA_LIST);
        this.handler(EntityPackets1_11.this.getTrackerHandler(Type.UNSIGNED_BYTE, 0));
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityType type = EntityPackets1_11.this.tracker(wrapper.user()).entityType(entityId);
            List<Metadata> list = wrapper.get(Types1_9.METADATA_LIST, 0);
            EntityPackets1_11.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), list, wrapper.user());
            EntityData entityData = EntityPackets1_11.this.entityDataForType(type);
            if (entityData != null) {
                wrapper.set(Type.UNSIGNED_BYTE, 0, (short)entityData.replacementId());
                if (entityData.hasBaseMeta()) {
                    entityData.defaultMeta().createMeta(new WrappedMetadata(list));
                }
            }
            if (list.isEmpty()) {
                list.add(new Metadata(0, MetaType1_9.Byte, (byte)0));
            }
        });
    }
}
