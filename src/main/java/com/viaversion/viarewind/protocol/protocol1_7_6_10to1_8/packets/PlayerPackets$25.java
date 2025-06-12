package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$25
extends PacketHandlers {
    PlayerPackets$25() {
    }

    @Override
    public void register() {
        this.map((Type)Type.INT, Type.VAR_INT);
        this.handler(packetWrapper -> packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.BYTE) - 1));
        this.map((Type)Type.INT, Type.VAR_INT);
        this.handler(packetWrapper -> {
            int action = packetWrapper.get(Type.VAR_INT, 1);
            if (action == 3 || action == 4) {
                PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
                playerSession.sprinting = action == 3;
                PacketWrapper abilitiesPacket = PacketWrapper.create(57, null, packetWrapper.user());
                abilitiesPacket.write(Type.BYTE, playerSession.combineAbilities());
                abilitiesPacket.write(Type.FLOAT, Float.valueOf(playerSession.sprinting ? playerSession.flySpeed * 2.0f : playerSession.flySpeed));
                abilitiesPacket.write(Type.FLOAT, Float.valueOf(playerSession.walkSpeed));
                PacketUtil.sendPacket(abilitiesPacket, Protocol1_7_6_10To1_8.class);
            }
        });
    }
}
