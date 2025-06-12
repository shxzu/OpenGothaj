package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_18$1
extends PacketHandlers {
    BlockItemPackets1_18$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.POSITION1_14);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            int data = wrapper.get(Type.INT, 1);
            if (id == 1010) {
                wrapper.set(Type.INT, 1, ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewItemId(data));
            }
        });
    }
}
