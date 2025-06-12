package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$1
extends PacketHandlers {
    EntityPackets$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(packetWrapper -> {
            byte status = packetWrapper.read(Type.BYTE);
            if (status > 23) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.write(Type.BYTE, status);
        });
    }
}
