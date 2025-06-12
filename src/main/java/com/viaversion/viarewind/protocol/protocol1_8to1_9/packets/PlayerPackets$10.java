package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$10
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    PlayerPackets$10(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            PacketWrapper animation = null;
            while ((animation = protocol.animationsToSend.poll()) != null) {
                PacketUtil.sendToServer(animation, Protocol1_8To1_9.class, true, true);
            }
            PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
            if (pos.getConfirmId() != -1) {
                return;
            }
            pos.setPos(packetWrapper.get(Type.DOUBLE, 0), packetWrapper.get(Type.DOUBLE, 1), packetWrapper.get(Type.DOUBLE, 2));
            pos.setOnGround(packetWrapper.get(Type.BOOLEAN, 0));
        });
        this.handler(packetWrapper -> packetWrapper.user().get(BossBarStorage.class).updateLocation());
    }
}
