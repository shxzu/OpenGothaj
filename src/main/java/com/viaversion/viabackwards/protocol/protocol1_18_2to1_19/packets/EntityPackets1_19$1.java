package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.StoredPainting;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;

class EntityPackets1_19$1
extends PacketHandlers {
    EntityPackets1_19$1() {
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
        this.handler(wrapper -> {
            byte headYaw = wrapper.read(Type.BYTE);
            int data = wrapper.read(Type.VAR_INT);
            EntityType entityType = EntityPackets1_19.this.trackAndMapEntity(wrapper);
            if (entityType.isOrHasParent(EntityTypes1_19.LIVINGENTITY)) {
                wrapper.write(Type.BYTE, headYaw);
                byte pitch = wrapper.get(Type.BYTE, 0);
                byte yaw = wrapper.get(Type.BYTE, 1);
                wrapper.set(Type.BYTE, 0, yaw);
                wrapper.set(Type.BYTE, 1, pitch);
                wrapper.setPacketType(ClientboundPackets1_18.SPAWN_MOB);
                return;
            }
            if (entityType == EntityTypes1_19.PAINTING) {
                wrapper.cancel();
                int entityId = wrapper.get(Type.VAR_INT, 0);
                StoredEntityData entityData = EntityPackets1_19.this.tracker(wrapper.user()).entityData(entityId);
                Position position = new Position(wrapper.get(Type.DOUBLE, 0).intValue(), wrapper.get(Type.DOUBLE, 1).intValue(), wrapper.get(Type.DOUBLE, 2).intValue());
                entityData.put(new StoredPainting(entityId, wrapper.get(Type.UUID, 0), position, data));
                return;
            }
            if (entityType == EntityTypes1_19.FALLING_BLOCK) {
                data = ((Protocol1_18_2To1_19)EntityPackets1_19.this.protocol).getMappingData().getNewBlockStateId(data);
            }
            wrapper.write(Type.INT, data);
        });
    }
}
