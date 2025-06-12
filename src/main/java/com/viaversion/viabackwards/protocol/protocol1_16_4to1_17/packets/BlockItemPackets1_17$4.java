package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_17$4
extends PacketHandlers {
    BlockItemPackets1_17$4() {
    }

    @Override
    public void register() {
        this.map(Type.LONG);
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            BlockChangeRecord[] records;
            long chunkPos = wrapper.get(Type.LONG, 0);
            int chunkY = (int)(chunkPos << 44 >> 44);
            if (chunkY < 0 || chunkY > 15) {
                wrapper.cancel();
                return;
            }
            for (BlockChangeRecord record : records = wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                record.setBlockId(((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
            }
        });
    }
}
