package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$6
extends PacketHandlers {
    PlayerPackets$6() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.handler(packetWrapper -> packetWrapper.user().get(EntityTracker.class).setPlayerGamemode(packetWrapper.get(Type.UNSIGNED_BYTE, 1).shortValue()));
        this.handler(packetWrapper -> {
            packetWrapper.user().get(BossBarStorage.class).updateLocation();
            packetWrapper.user().get(BossBarStorage.class).changeWorld();
        });
        this.handler(packetWrapper -> {
            ClientWorld world = packetWrapper.user().get(ClientWorld.class);
            world.setEnvironment(packetWrapper.get(Type.INT, 0));
        });
    }
}
