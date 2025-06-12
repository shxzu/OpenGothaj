package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$8
extends PacketHandlers {
    PlayerPackets$8() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            int type = packetWrapper.get(Type.VAR_INT, 1);
            if (type == 2) {
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            if (type == 2 || type == 0) {
                packetWrapper.write(Type.VAR_INT, 0);
            }
        });
    }
}
