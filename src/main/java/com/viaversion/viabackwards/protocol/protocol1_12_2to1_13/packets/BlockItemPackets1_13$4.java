package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$4
extends PacketHandlers {
    BlockItemPackets1_13$4() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
        this.handler(wrapper -> {
            BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
            for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                int chunkX = wrapper.get(Type.INT, 0);
                int chunkZ = wrapper.get(Type.INT, 1);
                int block = record.getBlockId();
                Position position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                storage.checkAndStore(position, block);
                BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
                record.setBlockId(((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(block));
            }
        });
    }
}
