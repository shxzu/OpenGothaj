package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_16$2
extends PacketHandlers {
    BlockItemPackets1_16$2() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.handler(wrapper -> {
            short property = wrapper.get(Type.SHORT, 0);
            if (property >= 4 && property <= 6) {
                short enchantmentId = wrapper.get(Type.SHORT, 1);
                if (enchantmentId > 11) {
                    enchantmentId = (short)(enchantmentId - 1);
                    wrapper.set(Type.SHORT, 1, enchantmentId);
                } else if (enchantmentId == 11) {
                    wrapper.set(Type.SHORT, 1, (short)9);
                }
            }
        });
    }
}
