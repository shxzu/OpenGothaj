package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$16
extends PacketHandlers {
    PlayerPackets$16() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String msg = packetWrapper.get(Type.STRING, 0);
            int gamemode = packetWrapper.user().get(PlayerSessionStorage.class).gameMode;
            if (gamemode == 3 && msg.toLowerCase().startsWith("/stp ")) {
                String username = msg.split(" ")[1];
                GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                GameProfileStorage.GameProfile profile = storage.get(username, true);
                if (profile != null && profile.uuid != null) {
                    packetWrapper.cancel();
                    PacketWrapper teleportPacket = PacketWrapper.create(24, null, packetWrapper.user());
                    teleportPacket.write(Type.UUID, profile.uuid);
                    PacketUtil.sendToServer(teleportPacket, Protocol1_7_6_10To1_8.class, true, true);
                }
            }
        });
    }
}
