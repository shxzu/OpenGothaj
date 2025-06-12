package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_17$6
extends PacketHandlers {
    BlockItemPackets1_17$6() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int y = wrapper.passthrough(Type.POSITION1_14).y();
            if (y < 0 || y > 255) {
                wrapper.cancel();
            }
        });
    }
}
