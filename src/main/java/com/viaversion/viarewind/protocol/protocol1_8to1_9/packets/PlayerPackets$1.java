package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class PlayerPackets$1
extends PacketHandlers {
    PlayerPackets$1() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            UUID uuid = packetWrapper.read(Type.UUID);
            int action = packetWrapper.read(Type.VAR_INT);
            BossBarStorage bossBarStorage = packetWrapper.user().get(BossBarStorage.class);
            if (action == 0) {
                bossBarStorage.add(uuid, ChatUtil.jsonToLegacy(packetWrapper.read(Type.COMPONENT)), packetWrapper.read(Type.FLOAT).floatValue());
                packetWrapper.read(Type.VAR_INT);
                packetWrapper.read(Type.VAR_INT);
                packetWrapper.read(Type.UNSIGNED_BYTE);
            } else if (action == 1) {
                bossBarStorage.remove(uuid);
            } else if (action == 2) {
                bossBarStorage.updateHealth(uuid, packetWrapper.read(Type.FLOAT).floatValue());
            } else if (action == 3) {
                String title = ChatUtil.jsonToLegacy(packetWrapper.read(Type.COMPONENT));
                bossBarStorage.updateTitle(uuid, title);
            }
        });
    }
}
