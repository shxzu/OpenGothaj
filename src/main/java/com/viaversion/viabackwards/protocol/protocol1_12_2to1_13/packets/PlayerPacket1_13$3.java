package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.TabCompleteStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class PlayerPacket1_13$3
extends PacketHandlers {
    PlayerPacket1_13$3() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            TabCompleteStorage storage = packetWrapper.user().get(TabCompleteStorage.class);
            int action = packetWrapper.passthrough(Type.VAR_INT);
            int nPlayers = packetWrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < nPlayers; ++i) {
                UUID uuid = packetWrapper.passthrough(Type.UUID);
                if (action == 0) {
                    String name = packetWrapper.passthrough(Type.STRING);
                    storage.usernames().put(uuid, name);
                    int nProperties = packetWrapper.passthrough(Type.VAR_INT);
                    for (int j = 0; j < nProperties; ++j) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.OPTIONAL_STRING);
                    }
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
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
                if (action == 3) {
                    packetWrapper.passthrough(Type.OPTIONAL_COMPONENT);
                    continue;
                }
                if (action != 4) continue;
                storage.usernames().remove(uuid);
            }
        });
    }
}
