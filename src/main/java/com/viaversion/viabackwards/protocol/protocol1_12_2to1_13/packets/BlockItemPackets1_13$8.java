package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$8
extends PacketHandlers {
    BlockItemPackets1_13$8() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            int iconCount = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < iconCount; ++i) {
                int type = wrapper.read(Type.VAR_INT);
                byte x = wrapper.read(Type.BYTE);
                byte z = wrapper.read(Type.BYTE);
                byte direction = wrapper.read(Type.BYTE);
                wrapper.read(Type.OPTIONAL_COMPONENT);
                if (type > 9) {
                    wrapper.set(Type.VAR_INT, 1, wrapper.get(Type.VAR_INT, 1) - 1);
                    continue;
                }
                wrapper.write(Type.BYTE, (byte)(type << 4 | direction & 0xF));
                wrapper.write(Type.BYTE, x);
                wrapper.write(Type.BYTE, z);
            }
        });
    }
}
