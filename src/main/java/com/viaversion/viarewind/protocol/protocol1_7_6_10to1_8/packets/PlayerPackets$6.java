package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$6
extends PacketHandlers {
    PlayerPackets$6() {
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(wrapper -> {
            double x = wrapper.get(Type.DOUBLE, 0);
            double y = wrapper.get(Type.DOUBLE, 1);
            double z = wrapper.get(Type.DOUBLE, 2);
            float yaw = wrapper.get(Type.FLOAT, 0).floatValue();
            float pitch = wrapper.get(Type.FLOAT, 1).floatValue();
            PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
            byte flags = wrapper.read(Type.BYTE);
            if ((flags & 1) == 1) {
                wrapper.set(Type.DOUBLE, 0, x + playerSession.getPosX());
            }
            if ((flags & 2) == 2) {
                y += playerSession.getPosY();
            }
            playerSession.receivedPosY = y;
            wrapper.set(Type.DOUBLE, 1, y + (double)1.62f);
            if ((flags & 4) == 4) {
                wrapper.set(Type.DOUBLE, 2, z + playerSession.getPosZ());
            }
            if ((flags & 8) == 8) {
                wrapper.set(Type.FLOAT, 0, Float.valueOf(yaw + playerSession.yaw));
            }
            if ((flags & 0x10) == 16) {
                wrapper.set(Type.FLOAT, 1, Float.valueOf(pitch + playerSession.pitch));
            }
            wrapper.write(Type.BOOLEAN, playerSession.onGround);
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            if (tracker.spectatingPlayerId != tracker.getPlayerId()) {
                wrapper.cancel();
            }
        });
    }
}
