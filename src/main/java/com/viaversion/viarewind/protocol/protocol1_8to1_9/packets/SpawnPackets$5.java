package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SpawnPackets$5
extends PacketHandlers {
    SpawnPackets$5() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.read(Type.UUID);
        this.map(Type.STRING);
        this.map(Type.POSITION1_8);
        this.map((Type)Type.BYTE, Type.UNSIGNED_BYTE);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.PAINTING);
            tracker.sendMetadataBuffer(entityId);
        });
    }
}
