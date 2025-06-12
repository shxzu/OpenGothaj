package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$17
extends PacketHandlers {
    PlayerPackets$17() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            PlayerPosition pos;
            int action = packetWrapper.get(Type.VAR_INT, 1);
            if (action == 6) {
                packetWrapper.set(Type.VAR_INT, 1, 7);
            } else if (action == 0 && !(pos = packetWrapper.user().get(PlayerPosition.class)).isOnGround()) {
                PacketWrapper elytra = PacketWrapper.create(20, null, packetWrapper.user());
                elytra.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                elytra.write(Type.VAR_INT, 8);
                elytra.write(Type.VAR_INT, 0);
                PacketUtil.sendToServer(elytra, Protocol1_8To1_9.class, true, false);
            }
        });
    }
}
