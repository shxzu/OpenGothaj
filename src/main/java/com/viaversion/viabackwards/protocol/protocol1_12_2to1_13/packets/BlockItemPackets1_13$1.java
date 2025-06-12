package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$1
extends PacketHandlers {
    BlockItemPackets1_13$1() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int blockId = wrapper.get(Type.VAR_INT, 0);
            if (blockId == 73) {
                blockId = 25;
            } else if (blockId == 99) {
                blockId = 33;
            } else if (blockId == 92) {
                blockId = 29;
            } else if (blockId == 142) {
                blockId = 54;
            } else if (blockId == 305) {
                blockId = 146;
            } else if (blockId == 249) {
                blockId = 130;
            } else if (blockId == 257) {
                blockId = 138;
            } else if (blockId == 140) {
                blockId = 52;
            } else if (blockId == 472) {
                blockId = 209;
            } else if (blockId >= 483 && blockId <= 498) {
                blockId = blockId - 483 + 219;
            }
            wrapper.set(Type.VAR_INT, 0, blockId);
        });
    }
}
