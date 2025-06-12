package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_13$1
extends PacketHandlers {
    EntityPackets1_13$1() {
    }

    @Override
    public void register() {
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.BYTE);
        this.handler(wrapper -> {
            if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                return;
            }
            PlayerPositionStorage1_13 playerStorage = wrapper.user().get(PlayerPositionStorage1_13.class);
            byte bitField = wrapper.get(Type.BYTE, 0);
            playerStorage.setX(EntityPackets1_13.toSet(bitField, 0, playerStorage.getX(), wrapper.get(Type.DOUBLE, 0)));
            playerStorage.setY(EntityPackets1_13.toSet(bitField, 1, playerStorage.getY(), wrapper.get(Type.DOUBLE, 1)));
            playerStorage.setZ(EntityPackets1_13.toSet(bitField, 2, playerStorage.getZ(), wrapper.get(Type.DOUBLE, 2)));
        });
    }
}
