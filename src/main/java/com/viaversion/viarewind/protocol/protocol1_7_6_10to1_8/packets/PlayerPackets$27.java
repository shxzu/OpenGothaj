package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.ComponentUtil;

class PlayerPackets$27
extends PacketHandlers {
    PlayerPackets$27() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            int x = packetWrapper.read(Type.INT);
            short y = packetWrapper.read(Type.SHORT);
            int z = packetWrapper.read(Type.INT);
            packetWrapper.write(Type.POSITION1_8, new Position(x, (int)y, z));
            for (int i = 0; i < 4; ++i) {
                String line = packetWrapper.read(Type.STRING);
                packetWrapper.write(Type.COMPONENT, ComponentUtil.legacyToJson(line));
            }
        });
    }
}
