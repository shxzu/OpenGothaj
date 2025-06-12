package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$22
extends PacketHandlers {
    PlayerPackets$22() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            int x = packetWrapper.read(Type.INT);
            short y = packetWrapper.read(Type.UNSIGNED_BYTE);
            int z = packetWrapper.read(Type.INT);
            packetWrapper.write(Type.POSITION1_8, new Position(x, (int)y, z));
        });
    }
}
