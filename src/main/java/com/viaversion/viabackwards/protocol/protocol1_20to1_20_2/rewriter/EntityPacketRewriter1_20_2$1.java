package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;

class EntityPacketRewriter1_20_2$1
extends PacketHandlers {
    EntityPacketRewriter1_20_2$1() {
    }

    @Override
    protected void register() {
        this.handler(wrapper -> {
            int entityId = wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.UUID);
            int entityType = wrapper.read(Type.VAR_INT);
            EntityPacketRewriter1_20_2.this.tracker(wrapper.user()).addEntity(entityId, EntityPacketRewriter1_20_2.this.typeFromId(entityType));
            if (entityType != EntityTypes1_19_4.PLAYER.getId()) {
                wrapper.write(Type.VAR_INT, entityType);
                if (entityType == EntityTypes1_19_4.FALLING_BLOCK.getId()) {
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.BYTE);
                    wrapper.passthrough(Type.BYTE);
                    wrapper.passthrough(Type.BYTE);
                    int blockState = wrapper.read(Type.VAR_INT);
                    wrapper.write(Type.VAR_INT, ((Protocol1_20To1_20_2)EntityPacketRewriter1_20_2.this.protocol).getMappingData().getNewBlockStateId(blockState));
                }
                return;
            }
            wrapper.setPacketType(ClientboundPackets1_19_4.SPAWN_PLAYER);
            wrapper.passthrough(Type.DOUBLE);
            wrapper.passthrough(Type.DOUBLE);
            wrapper.passthrough(Type.DOUBLE);
            byte pitch = wrapper.read(Type.BYTE);
            wrapper.passthrough(Type.BYTE);
            wrapper.write(Type.BYTE, pitch);
            wrapper.read(Type.BYTE);
            wrapper.read(Type.VAR_INT);
            short velocityX = wrapper.read(Type.SHORT);
            short velocityY = wrapper.read(Type.SHORT);
            short velocityZ = wrapper.read(Type.SHORT);
            if (velocityX == 0 && velocityY == 0 && velocityZ == 0) {
                return;
            }
            wrapper.send(Protocol1_20To1_20_2.class);
            wrapper.cancel();
            PacketWrapper velocityPacket = wrapper.create(ClientboundPackets1_19_4.ENTITY_VELOCITY);
            velocityPacket.write(Type.VAR_INT, entityId);
            velocityPacket.write(Type.SHORT, velocityX);
            velocityPacket.write(Type.SHORT, velocityY);
            velocityPacket.write(Type.SHORT, velocityZ);
            velocityPacket.send(Protocol1_20To1_20_2.class);
        });
    }
}
