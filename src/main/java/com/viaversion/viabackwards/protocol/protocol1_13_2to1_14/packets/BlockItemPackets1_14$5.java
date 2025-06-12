package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_14$5
extends PacketHandlers {
    BlockItemPackets1_14$5() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14, Type.POSITION1_8);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.VAR_INT, 0);
            wrapper.set(Type.VAR_INT, 0, ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(id));
        });
    }
}
