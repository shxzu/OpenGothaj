package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$19
extends PacketHandlers {
    PlayerPackets$19() {
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.read(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            double x = packetWrapper.get(Type.DOUBLE, 0);
            double feetY = packetWrapper.get(Type.DOUBLE, 1);
            double z = packetWrapper.get(Type.DOUBLE, 2);
            PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
            playerSession.onGround = packetWrapper.get(Type.BOOLEAN, 0);
            playerSession.setPos(x, feetY, z);
        });
    }
}
