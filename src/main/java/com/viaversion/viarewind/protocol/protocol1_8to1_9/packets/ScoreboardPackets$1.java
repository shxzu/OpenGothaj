package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class ScoreboardPackets$1
extends PacketHandlers {
    ScoreboardPackets$1() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.handler(packetWrapper -> {
            byte mode = packetWrapper.get(Type.BYTE, 0);
            if (mode == 0 || mode == 2) {
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.BYTE);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.read(Type.STRING);
                packetWrapper.passthrough(Type.BYTE);
            }
            if (mode == 0 || mode == 3 || mode == 4) {
                packetWrapper.passthrough(Type.STRING_ARRAY);
            }
        });
    }
}
