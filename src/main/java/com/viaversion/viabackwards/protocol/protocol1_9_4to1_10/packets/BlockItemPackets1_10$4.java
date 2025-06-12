package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_10$4
extends PacketHandlers {
    BlockItemPackets1_10$4() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            if (id == 46) {
                wrapper.set(Type.INT, 0, 38);
            }
        });
    }
}
