package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets1_13_2$5
extends PacketHandlers {
    InventoryPackets1_13_2$5() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.VAR_INT);
        this.map(Type.ITEM1_13, Type.ITEM1_13_2);
    }
}
