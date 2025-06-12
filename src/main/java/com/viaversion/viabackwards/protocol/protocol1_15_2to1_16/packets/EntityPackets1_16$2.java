package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;

class EntityPackets1_16$2
extends PacketHandlers {
    EntityPackets1_16$2() {
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
        this.map(Type.INT);
        this.handler(wrapper -> {
            EntityType entityType = EntityPackets1_16.this.typeFromId(wrapper.get(Type.VAR_INT, 1));
            if (entityType == EntityTypes1_16.LIGHTNING_BOLT) {
                wrapper.cancel();
                PacketWrapper spawnLightningPacket = wrapper.create(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY);
                spawnLightningPacket.write(Type.VAR_INT, wrapper.get(Type.VAR_INT, 0));
                spawnLightningPacket.write(Type.BYTE, (byte)1);
                spawnLightningPacket.write(Type.DOUBLE, wrapper.get(Type.DOUBLE, 0));
                spawnLightningPacket.write(Type.DOUBLE, wrapper.get(Type.DOUBLE, 1));
                spawnLightningPacket.write(Type.DOUBLE, wrapper.get(Type.DOUBLE, 2));
                spawnLightningPacket.send(Protocol1_15_2To1_16.class);
            }
        });
        this.handler(EntityPackets1_16.this.getSpawnTrackerWithDataHandler(EntityTypes1_16.FALLING_BLOCK));
    }
}
