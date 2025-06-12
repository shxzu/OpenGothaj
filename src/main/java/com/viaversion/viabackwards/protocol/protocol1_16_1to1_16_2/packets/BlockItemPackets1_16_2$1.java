package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_16_2$1
extends PacketHandlers {
    BlockItemPackets1_16_2$1() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14);
        this.map(Type.UNSIGNED_BYTE);
        this.handler(wrapper -> BlockItemPackets1_16_2.this.handleBlockEntity(wrapper.passthrough(Type.NAMED_COMPOUND_TAG)));
    }
}
