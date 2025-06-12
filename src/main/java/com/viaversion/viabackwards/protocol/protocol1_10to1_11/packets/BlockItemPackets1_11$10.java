package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;

class BlockItemPackets1_11$10
extends PacketHandlers {
    BlockItemPackets1_11$10() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            WindowTracker windowTracker = wrapper.user().get(WindowTracker.class);
            windowTracker.setInventory(null);
            windowTracker.setEntityId(-1);
        });
    }
}
