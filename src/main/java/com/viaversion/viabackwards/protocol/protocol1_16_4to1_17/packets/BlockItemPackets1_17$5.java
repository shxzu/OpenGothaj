package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_17$5
extends PacketHandlers {
    BlockItemPackets1_17$5() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int y = wrapper.get(Type.POSITION1_14, 0).y();
            if (y < 0 || y > 255) {
                wrapper.cancel();
                return;
            }
            wrapper.set(Type.VAR_INT, 0, ((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(wrapper.get(Type.VAR_INT, 0)));
        });
    }
}
