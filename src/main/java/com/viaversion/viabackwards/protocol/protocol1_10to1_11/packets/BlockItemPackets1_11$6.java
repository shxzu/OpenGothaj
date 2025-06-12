package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_11$6
extends PacketHandlers {
    BlockItemPackets1_11$6() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
        this.handler(wrapper -> {
            for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                record.setBlockId(BlockItemPackets1_11.this.handleBlockID(record.getBlockId()));
            }
        });
    }
}
