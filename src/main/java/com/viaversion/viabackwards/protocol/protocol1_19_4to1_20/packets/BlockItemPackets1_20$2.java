package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;

import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_20$2
extends PacketHandlers {
    BlockItemPackets1_20$2() {
    }

    @Override
    public void register() {
        this.map(Type.LONG);
        this.create(Type.BOOLEAN, false);
        this.handler(wrapper -> {
            for (BlockChangeRecord record : wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                record.setBlockId(((Protocol1_19_4To1_20)BlockItemPackets1_20.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
            }
        });
    }
}
