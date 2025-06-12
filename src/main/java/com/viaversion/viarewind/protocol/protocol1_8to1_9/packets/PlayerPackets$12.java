package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$12
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    PlayerPackets$12(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            PacketWrapper animation = null;
            while ((animation = protocol.animationsToSend.poll()) != null) {
                PacketUtil.sendToServer(animation, Protocol1_8To1_9.class, true, true);
            }
            double x = packetWrapper.get(Type.DOUBLE, 0);
            double y = packetWrapper.get(Type.DOUBLE, 1);
            double z = packetWrapper.get(Type.DOUBLE, 2);
            float yaw = packetWrapper.get(Type.FLOAT, 0).floatValue();
            float pitch = packetWrapper.get(Type.FLOAT, 1).floatValue();
            boolean onGround = packetWrapper.get(Type.BOOLEAN, 0);
            PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
            if (pos.getConfirmId() != -1) {
                if (pos.getPosX() == x && pos.getPosY() == y && pos.getPosZ() == z && pos.getYaw() == yaw && pos.getPitch() == pitch) {
                    PacketWrapper confirmTeleport = packetWrapper.create(0);
                    confirmTeleport.write(Type.VAR_INT, pos.getConfirmId());
                    PacketUtil.sendToServer(confirmTeleport, Protocol1_8To1_9.class, true, true);
                    pos.setConfirmId(-1);
                }
            } else {
                pos.setPos(x, y, z);
                pos.setYaw(yaw);
                pos.setPitch(pitch);
                pos.setOnGround(onGround);
                packetWrapper.user().get(BossBarStorage.class).updateLocation();
            }
        });
    }
}
