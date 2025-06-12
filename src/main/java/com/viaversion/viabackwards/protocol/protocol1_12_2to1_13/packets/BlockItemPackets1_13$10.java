package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$10
extends PacketHandlers {
    BlockItemPackets1_13$10() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.handler(wrapper -> {
            short property = wrapper.get(Type.SHORT, 0);
            if (property >= 4 && property <= 6) {
                short oldId = wrapper.get(Type.SHORT, 1);
                wrapper.set(Type.SHORT, 1, (short)((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(oldId));
            }
        });
    }
}
