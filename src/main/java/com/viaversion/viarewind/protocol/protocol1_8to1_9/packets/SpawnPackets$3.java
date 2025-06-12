package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SpawnPackets$3
extends PacketHandlers {
    SpawnPackets$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.LIGHTNING);
            tracker.sendMetadataBuffer(entityId);
        });
    }
}
