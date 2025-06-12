package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$7
extends PacketHandlers {
    BlockItemPackets1_13$7() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.POSITION1_8);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            int data = wrapper.get(Type.INT, 1);
            if (id == 1010) {
                wrapper.set(Type.INT, 1, ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getItemMappings().getNewId(data) >> 4);
            } else if (id == 2001) {
                data = ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(data);
                int blockId = data >> 4;
                int blockData = data & 0xF;
                wrapper.set(Type.INT, 1, blockId & 0xFFF | blockData << 12);
            }
        });
    }
}
