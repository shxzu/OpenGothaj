package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

class EntityPackets1_11$5
extends PacketHandlers {
    EntityPackets1_11$5() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BYTE);
        this.handler(wrapper -> {
            byte b = wrapper.get(Type.BYTE, 0);
            if (b == 35) {
                wrapper.clearPacket();
                wrapper.setPacketType(ClientboundPackets1_9_3.GAME_EVENT);
                wrapper.write(Type.UNSIGNED_BYTE, (short)10);
                wrapper.write(Type.FLOAT, Float.valueOf(0.0f));
            }
        });
    }
}
