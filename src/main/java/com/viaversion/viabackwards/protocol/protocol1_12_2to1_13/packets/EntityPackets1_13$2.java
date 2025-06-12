package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class EntityPackets1_13$2
extends PacketHandlers {
    EntityPackets1_13$2() {
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
        this.handler(EntityPackets1_13.this.getObjectTrackerHandler());
        this.handler(wrapper -> {
            Optional<EntityTypes1_13.ObjectType> optionalType = EntityTypes1_13.ObjectType.findById(wrapper.get(Type.BYTE, 0).byteValue());
            if (!optionalType.isPresent()) {
                return;
            }
            EntityTypes1_13.ObjectType type = optionalType.get();
            if (type == EntityTypes1_13.ObjectType.FALLING_BLOCK) {
                int blockState = wrapper.get(Type.INT, 0);
                int combined = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState);
                combined = combined >> 4 & 0xFFF | (combined & 0xF) << 12;
                wrapper.set(Type.INT, 0, combined);
            } else if (type == EntityTypes1_13.ObjectType.ITEM_FRAME) {
                int data = wrapper.get(Type.INT, 0);
                switch (data) {
                    case 3: {
                        data = 0;
                        break;
                    }
                    case 4: {
                        data = 1;
                        break;
                    }
                    case 5: {
                        data = 3;
                    }
                }
                wrapper.set(Type.INT, 0, data);
            } else if (type == EntityTypes1_13.ObjectType.TRIDENT) {
                wrapper.set(Type.BYTE, 0, (byte)EntityTypes1_13.ObjectType.TIPPED_ARROW.getId());
            }
        });
    }
}
