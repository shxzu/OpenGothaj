package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$2
extends PacketHandlers {
    WorldPackets$2() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            int block = packetWrapper.get(Type.VAR_INT, 0);
            if (block >= 219 && block <= 234) {
                block = 130;
                packetWrapper.set(Type.VAR_INT, 0, 130);
            }
        });
    }
}
