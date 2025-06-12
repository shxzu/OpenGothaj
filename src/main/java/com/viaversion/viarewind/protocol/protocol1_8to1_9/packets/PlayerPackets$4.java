package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$4
extends PacketHandlers {
    PlayerPackets$4() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            tracker.setPlayerId(packetWrapper.get(Type.INT, 0));
            tracker.setPlayerGamemode(packetWrapper.get(Type.UNSIGNED_BYTE, 0).shortValue());
            tracker.getClientEntityTypes().put(tracker.getPlayerId(), EntityTypes1_10.EntityType.ENTITY_HUMAN);
        });
        this.handler(packetWrapper -> {
            ClientWorld world = packetWrapper.user().get(ClientWorld.class);
            world.setEnvironment(packetWrapper.get(Type.BYTE, 0).byteValue());
        });
    }
}
