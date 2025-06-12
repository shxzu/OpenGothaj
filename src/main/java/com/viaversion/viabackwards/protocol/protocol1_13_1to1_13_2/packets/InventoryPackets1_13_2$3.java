package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets1_13_2$3
extends PacketHandlers {
    InventoryPackets1_13_2$3() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            String channel = wrapper.get(Type.STRING, 0);
            if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
                wrapper.passthrough(Type.INT);
                int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                for (int i = 0; i < size; ++i) {
                    wrapper.write(Type.ITEM1_13, wrapper.read(Type.ITEM1_13_2));
                    wrapper.write(Type.ITEM1_13, wrapper.read(Type.ITEM1_13_2));
                    boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                    if (secondItem) {
                        wrapper.write(Type.ITEM1_13, wrapper.read(Type.ITEM1_13_2));
                    }
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.INT);
                    wrapper.passthrough(Type.INT);
                }
            }
        });
    }
}
