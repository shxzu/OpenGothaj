package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class EntityPackets1_10$1
extends PacketHandlers {
    EntityPackets1_10$1() {
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
        this.handler(EntityPackets1_10.this.getObjectTrackerHandler());
        this.handler(EntityPackets1_10.this.getObjectRewriter(id -> EntityTypes1_11.ObjectType.findById(id.byteValue()).orElse(null)));
        this.handler(wrapper -> {
            Optional<EntityTypes1_12.ObjectType> type = EntityTypes1_12.ObjectType.findById(wrapper.get(Type.BYTE, 0).byteValue());
            if (type.isPresent() && type.get() == EntityTypes1_12.ObjectType.FALLING_BLOCK) {
                int objectData = wrapper.get(Type.INT, 0);
                int objType = objectData & 0xFFF;
                int data = objectData >> 12 & 0xF;
                Block block = ((Protocol1_9_4To1_10)EntityPackets1_10.this.protocol).getItemRewriter().handleBlock(objType, data);
                if (block == null) {
                    return;
                }
                wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
            }
        });
    }
}
