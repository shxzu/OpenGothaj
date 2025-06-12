package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19$1
extends PacketHandlers {
    BlockItemPackets1_19$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int size = wrapper.read(Type.VAR_INT);
            wrapper.write(Type.UNSIGNED_BYTE, (short)size);
            for (int i = 0; i < size; ++i) {
                BlockItemPackets1_19.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                BlockItemPackets1_19.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                Item secondItem = wrapper.read(Type.ITEM1_13_2);
                if (secondItem != null) {
                    BlockItemPackets1_19.this.handleItemToClient(secondItem);
                    wrapper.write(Type.BOOLEAN, true);
                    wrapper.write(Type.ITEM1_13_2, secondItem);
                } else {
                    wrapper.write(Type.BOOLEAN, false);
                }
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.INT);
            }
        });
    }
}
