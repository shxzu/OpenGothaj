package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_16$5
extends PacketHandlers {
    EntityPackets1_16$5() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            int action = packetWrapper.passthrough(Type.VAR_INT);
            int playerCount = packetWrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < playerCount; ++i) {
                packetWrapper.passthrough(Type.UUID);
                if (action == 0) {
                    packetWrapper.passthrough(Type.STRING);
                    int properties = packetWrapper.passthrough(Type.VAR_INT);
                    for (int j = 0; j < properties; ++j) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.OPTIONAL_STRING);
                    }
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.VAR_INT);
                    ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getTranslatableRewriter().processText(packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
                    continue;
                }
                if (action == 1) {
                    packetWrapper.passthrough(Type.VAR_INT);
                    continue;
                }
                if (action == 2) {
                    packetWrapper.passthrough(Type.VAR_INT);
                    continue;
                }
                if (action != 3) continue;
                ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getTranslatableRewriter().processText(packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
            }
        });
    }
}
