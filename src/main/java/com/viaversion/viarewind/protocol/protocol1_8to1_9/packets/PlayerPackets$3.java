package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$3
extends PacketHandlers {
    PlayerPackets$3() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.FLOAT);
        this.handler(packetWrapper -> {
            short reason = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            if (reason == 3) {
                packetWrapper.user().get(EntityTracker.class).setPlayerGamemode(packetWrapper.get(Type.FLOAT, 0).intValue());
            }
        });
    }
}
