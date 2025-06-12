package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$5
extends PacketHandlers {
    WorldPackets$5() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String name = packetWrapper.get(Type.STRING, 0);
            if ((name = SoundRemapper.getOldName(name)) == null) {
                packetWrapper.cancel();
            } else {
                packetWrapper.set(Type.STRING, 0, name);
            }
        });
        this.read(Type.VAR_INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.FLOAT);
        this.map(Type.UNSIGNED_BYTE);
    }
}
