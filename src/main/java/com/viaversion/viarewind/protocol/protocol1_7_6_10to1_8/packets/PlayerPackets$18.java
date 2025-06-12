package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$18
extends PacketHandlers {
    PlayerPackets$18() {
    }

    @Override
    public void register() {
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
            playerSession.onGround = packetWrapper.get(Type.BOOLEAN, 0);
        });
    }
}
