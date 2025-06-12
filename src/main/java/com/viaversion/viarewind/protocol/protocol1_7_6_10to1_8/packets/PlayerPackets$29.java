package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.List;

class PlayerPackets$29
extends PacketHandlers {
    PlayerPackets$29() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.create(Type.OPTIONAL_POSITION1_8, null);
        this.handler(packetWrapper -> {
            String msg = packetWrapper.get(Type.STRING, 0);
            if (msg.toLowerCase().startsWith("/stp ")) {
                packetWrapper.cancel();
                String[] args = msg.split(" ");
                if (args.length <= 2) {
                    String prefix = args.length == 1 ? "" : args[1];
                    GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                    List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
                    PacketWrapper tabComplete = PacketWrapper.create(58, null, packetWrapper.user());
                    tabComplete.write(Type.VAR_INT, profiles.size());
                    for (GameProfileStorage.GameProfile profile : profiles) {
                        tabComplete.write(Type.STRING, profile.name);
                    }
                    PacketUtil.sendPacket(tabComplete, Protocol1_7_6_10To1_8.class);
                }
            }
        });
    }
}
