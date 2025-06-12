package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;

final class BlockItemPackets1_16$EquipmentData {
    private final int slot;
    private final Item item;

    private BlockItemPackets1_16$EquipmentData(int slot, Item item) {
        this.slot = slot;
        this.item = item;
    }

    static int access$100(BlockItemPackets1_16$EquipmentData x0) {
        return x0.slot;
    }

    static Item access$200(BlockItemPackets1_16$EquipmentData x0) {
        return x0.item;
    }
}
