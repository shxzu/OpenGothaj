package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$5
extends PacketHandlers {
    PlayerPackets$5() {
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.BYTE);
        this.handler(packetWrapper -> {
            PlayerPosition pos = packetWrapper.user().get(PlayerPosition.class);
            int teleportId = packetWrapper.read(Type.VAR_INT);
            pos.setConfirmId(teleportId);
            byte flags = packetWrapper.get(Type.BYTE, 0);
            double x = packetWrapper.get(Type.DOUBLE, 0);
            double y = packetWrapper.get(Type.DOUBLE, 1);
            double z = packetWrapper.get(Type.DOUBLE, 2);
            float yaw = packetWrapper.get(Type.FLOAT, 0).floatValue();
            float pitch = packetWrapper.get(Type.FLOAT, 1).floatValue();
            packetWrapper.set(Type.BYTE, 0, (byte)0);
            if (flags != 0) {
                if ((flags & 1) != 0) {
                    packetWrapper.set(Type.DOUBLE, 0, x += pos.getPosX());
                }
                if ((flags & 2) != 0) {
                    packetWrapper.set(Type.DOUBLE, 1, y += pos.getPosY());
                }
                if ((flags & 4) != 0) {
                    packetWrapper.set(Type.DOUBLE, 2, z += pos.getPosZ());
                }
                if ((flags & 8) != 0) {
                    packetWrapper.set(Type.FLOAT, 0, Float.valueOf(yaw += pos.getYaw()));
                }
                if ((flags & 0x10) != 0) {
                    packetWrapper.set(Type.FLOAT, 1, Float.valueOf(pitch += pos.getPitch()));
                }
            }
            pos.setPos(x, y, z);
            pos.setYaw(yaw);
            pos.setPitch(pitch);
        });
    }
}
