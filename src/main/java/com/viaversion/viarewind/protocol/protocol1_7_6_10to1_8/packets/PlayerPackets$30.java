package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$30
extends PacketHandlers {
    PlayerPackets$30() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.read(Type.BYTE);
        this.handler(packetWrapper -> {
            boolean cape = packetWrapper.read(Type.BOOLEAN);
            packetWrapper.write(Type.UNSIGNED_BYTE, (short)(cape ? 127 : 126));
        });
    }
}
