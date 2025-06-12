package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_17$2
extends PacketHandlers {
    BlockItemPackets1_17$2() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            if (id == 16) {
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.read(Type.FLOAT);
                wrapper.read(Type.FLOAT);
                wrapper.read(Type.FLOAT);
            } else if (id == 37) {
                wrapper.set(Type.INT, 0, -1);
                wrapper.cancel();
            }
        });
        this.handler(BlockItemPackets1_17.this.getSpawnParticleHandler());
    }
}
