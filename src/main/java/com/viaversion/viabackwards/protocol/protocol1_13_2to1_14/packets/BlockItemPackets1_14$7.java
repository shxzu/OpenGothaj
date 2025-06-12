package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_14$7
extends PacketHandlers {
    BlockItemPackets1_14$7() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.POSITION1_14, Type.POSITION1_8);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            int data = wrapper.get(Type.INT, 1);
            if (id == 1010) {
                wrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewItemId(data));
            } else if (id == 2001) {
                wrapper.set(Type.INT, 1, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(data));
            }
        });
    }
}
