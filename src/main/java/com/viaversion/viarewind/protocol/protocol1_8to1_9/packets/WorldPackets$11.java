package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$11
extends PacketHandlers {
    WorldPackets$11() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            int soundId = packetWrapper.read(Type.VAR_INT);
            String sound = SoundRemapper.oldNameFromId(soundId);
            if (sound == null) {
                packetWrapper.cancel();
            } else {
                packetWrapper.write(Type.STRING, sound);
            }
        });
        this.handler(packetWrapper -> packetWrapper.read(Type.VAR_INT));
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.FLOAT);
        this.map(Type.UNSIGNED_BYTE);
    }
}
