package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_10$3
extends PacketHandlers {
    BlockItemPackets1_10$3() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
        this.handler(wrapper -> {
            for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                record.setBlockId(BlockItemPackets1_10.this.handleBlockID(record.getBlockId()));
            }
        });
    }
}
