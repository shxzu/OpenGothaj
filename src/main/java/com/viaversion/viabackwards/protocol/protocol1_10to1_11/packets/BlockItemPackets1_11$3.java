package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_11$3
extends PacketHandlers {
    BlockItemPackets1_11$3() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            if (wrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                wrapper.passthrough(Type.INT);
                int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                for (int i = 0; i < size; ++i) {
                    wrapper.write(Type.ITEM1_8, BlockItemPackets1_11.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                    wrapper.write(Type.ITEM1_8, BlockItemPackets1_11.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                    boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                    if (secondItem) {
                        wrapper.write(Type.ITEM1_8, BlockItemPackets1_11.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                    }
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.INT);
                    wrapper.passthrough(Type.INT);
                }
            }
        });
    }
}
