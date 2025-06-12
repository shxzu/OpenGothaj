package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$3
extends PacketHandlers {
    BlockItemPackets1_13$3() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.handler(wrapper -> {
            int blockState = wrapper.read(Type.VAR_INT);
            Position position = wrapper.get(Type.POSITION1_8, 0);
            BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
            storage.checkAndStore(position, blockState);
            wrapper.write(Type.VAR_INT, ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(blockState));
            BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
        });
    }
}
