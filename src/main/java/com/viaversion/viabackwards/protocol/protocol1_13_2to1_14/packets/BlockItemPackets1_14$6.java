package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_14$6
extends PacketHandlers {
    BlockItemPackets1_14$6() {
    }

    @Override
    public void register() {
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(wrapper -> {
            for (int i = 0; i < 3; ++i) {
                float coord = wrapper.get(Type.FLOAT, i).floatValue();
                if (!(coord < 0.0f)) continue;
                coord = (float)Math.floor(coord);
                wrapper.set(Type.FLOAT, i, Float.valueOf(coord));
            }
        });
    }
}
