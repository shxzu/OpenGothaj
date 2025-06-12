package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$11
extends PacketHandlers {
    PlayerPackets$11() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(packetWrapper -> {
            byte flags = packetWrapper.get(Type.BYTE, 0);
            float flySpeed = packetWrapper.get(Type.FLOAT, 0).floatValue();
            float walkSpeed = packetWrapper.get(Type.FLOAT, 1).floatValue();
            PlayerSessionStorage abilities = packetWrapper.user().get(PlayerSessionStorage.class);
            abilities.invincible = (flags & 8) == 8;
            abilities.allowFly = (flags & 4) == 4;
            abilities.flying = (flags & 2) == 2;
            abilities.creative = (flags & 1) == 1;
            abilities.flySpeed = flySpeed;
            abilities.walkSpeed = walkSpeed;
            if (abilities.sprinting && abilities.flying) {
                packetWrapper.set(Type.FLOAT, 0, Float.valueOf(abilities.flySpeed * 2.0f));
            }
        });
    }
}
