package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$28
extends PacketHandlers {
    PlayerPackets$28() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(packetWrapper -> {
            PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
            if (playerSession.allowFly) {
                byte flags = packetWrapper.get(Type.BYTE, 0);
                playerSession.flying = (flags & 2) == 2;
            }
            packetWrapper.set(Type.FLOAT, 0, Float.valueOf(playerSession.flySpeed));
        });
    }
}
