package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_12$5
extends PacketHandlers {
    EntityPackets1_12$5() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int size;
            int newSize = size = wrapper.get(Type.INT, 0).intValue();
            for (int i = 0; i < size; ++i) {
                int j;
                int modSize;
                String key = wrapper.read(Type.STRING);
                if (key.equals("generic.flyingSpeed")) {
                    --newSize;
                    wrapper.read(Type.DOUBLE);
                    modSize = wrapper.read(Type.VAR_INT);
                    for (j = 0; j < modSize; ++j) {
                        wrapper.read(Type.UUID);
                        wrapper.read(Type.DOUBLE);
                        wrapper.read(Type.BYTE);
                    }
                    continue;
                }
                wrapper.write(Type.STRING, key);
                wrapper.passthrough(Type.DOUBLE);
                modSize = wrapper.passthrough(Type.VAR_INT);
                for (j = 0; j < modSize; ++j) {
                    wrapper.passthrough(Type.UUID);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.BYTE);
                }
            }
            if (newSize != size) {
                wrapper.set(Type.INT, 0, newSize);
            }
        });
    }
}
