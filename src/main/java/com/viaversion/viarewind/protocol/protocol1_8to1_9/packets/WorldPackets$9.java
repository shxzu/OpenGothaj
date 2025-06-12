package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$9
extends PacketHandlers {
    WorldPackets$9() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(packetWrapper -> {
            int type = packetWrapper.get(Type.INT, 0);
            if (type > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                packetWrapper.cancel();
                return;
            }
            if (type == 42) {
                packetWrapper.set(Type.INT, 0, 24);
            } else if (type == 43) {
                packetWrapper.set(Type.INT, 0, 3);
            } else if (type == 44) {
                packetWrapper.set(Type.INT, 0, 34);
            } else if (type == 45) {
                packetWrapper.set(Type.INT, 0, 1);
            }
        });
    }
}
