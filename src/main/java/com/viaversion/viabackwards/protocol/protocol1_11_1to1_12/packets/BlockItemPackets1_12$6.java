package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_12$6
extends PacketHandlers {
    BlockItemPackets1_12$6() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.handler(wrapper -> {
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                wrapper.cancel();
            }
        });
    }
}
