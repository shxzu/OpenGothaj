package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_13_1$1
extends PacketHandlers {
    EntityPackets1_13_1$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.BYTE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            byte type = wrapper.get(Type.BYTE, 0);
            EntityTypes1_13.EntityType entType = EntityTypes1_13.getTypeFromId(type, true);
            if (entType == null) {
                ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + type);
                return;
            }
            if (entType.is((EntityType)EntityTypes1_13.EntityType.FALLING_BLOCK)) {
                int data = wrapper.get(Type.INT, 0);
                wrapper.set(Type.INT, 0, ((Protocol1_13To1_13_1)EntityPackets1_13_1.this.protocol).getMappingData().getNewBlockStateId(data));
            }
            EntityPackets1_13_1.this.tracker(wrapper.user()).addEntity(entityId, entType);
        });
    }
}
