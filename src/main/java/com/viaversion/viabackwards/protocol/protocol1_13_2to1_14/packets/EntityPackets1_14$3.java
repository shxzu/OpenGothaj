package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_14$3
extends PacketHandlers {
    EntityPackets1_14$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map((Type)Type.VAR_INT, Type.BYTE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.handler(EntityPackets1_14.this.getObjectTrackerHandler());
        this.handler(wrapper -> {
            int data;
            EntityTypes1_13.ObjectType objectType;
            byte id = wrapper.get(Type.BYTE, 0);
            int mappedId = EntityPackets1_14.this.newEntityId(id);
            EntityTypes1_13.EntityType entityType = EntityTypes1_13.getTypeFromId(mappedId, false);
            if (entityType.isOrHasParent(EntityTypes1_13.EntityType.MINECART_ABSTRACT)) {
                objectType = EntityTypes1_13.ObjectType.MINECART;
                data = 0;
                switch (entityType) {
                    case CHEST_MINECART: {
                        data = 1;
                        break;
                    }
                    case FURNACE_MINECART: {
                        data = 2;
                        break;
                    }
                    case TNT_MINECART: {
                        data = 3;
                        break;
                    }
                    case SPAWNER_MINECART: {
                        data = 4;
                        break;
                    }
                    case HOPPER_MINECART: {
                        data = 5;
                        break;
                    }
                    case COMMAND_BLOCK_MINECART: {
                        data = 6;
                    }
                }
                if (data != 0) {
                    wrapper.set(Type.INT, 0, data);
                }
            } else {
                objectType = EntityTypes1_13.ObjectType.fromEntityType(entityType).orElse(null);
            }
            if (objectType == null) {
                return;
            }
            wrapper.set(Type.BYTE, 0, (byte)objectType.getId());
            data = wrapper.get(Type.INT, 0);
            if (objectType == EntityTypes1_13.ObjectType.FALLING_BLOCK) {
                int blockState = wrapper.get(Type.INT, 0);
                int combined = ((Protocol1_13_2To1_14)EntityPackets1_14.this.protocol).getMappingData().getNewBlockStateId(blockState);
                wrapper.set(Type.INT, 0, combined);
            } else if (entityType.isOrHasParent(EntityTypes1_13.EntityType.ABSTRACT_ARROW)) {
                wrapper.set(Type.INT, 0, data + 1);
            }
        });
    }
}
