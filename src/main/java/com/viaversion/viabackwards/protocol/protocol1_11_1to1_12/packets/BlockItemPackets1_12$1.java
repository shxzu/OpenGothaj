package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.MapColorMapping;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_12$1
extends PacketHandlers {
    BlockItemPackets1_12$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            int count = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < count * 3; ++i) {
                wrapper.passthrough(Type.BYTE);
            }
        });
        this.handler(wrapper -> {
            short columns = wrapper.passthrough(Type.UNSIGNED_BYTE);
            if (columns <= 0) {
                return;
            }
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            byte[] data = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            for (int i = 0; i < data.length; ++i) {
                short color = (short)(data[i] & 0xFF);
                if (color <= 143) continue;
                color = (short)MapColorMapping.getNearestOldColor(color);
                data[i] = (byte)color;
            }
            wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, data);
        });
    }
}
