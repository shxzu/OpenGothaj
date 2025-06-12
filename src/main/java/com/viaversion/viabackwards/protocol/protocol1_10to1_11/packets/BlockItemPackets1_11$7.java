package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;

class BlockItemPackets1_11$7
extends PacketHandlers {
    BlockItemPackets1_11$7() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.handler(wrapper -> {
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 10) {
                wrapper.cancel();
            }
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 1) {
                CompoundTag tag = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
                EntityIdRewriter.toClientSpawner(tag, true);
            }
        });
    }
}
