package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_11$8
extends PacketHandlers {
    BlockItemPackets1_11$8() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.map(Type.COMPONENT);
        this.map(Type.UNSIGNED_BYTE);
        this.handler(wrapper -> {
            int entityId = -1;
            if (wrapper.get(Type.STRING, 0).equals("EntityHorse")) {
                entityId = wrapper.passthrough(Type.INT);
            }
            String inventory = wrapper.get(Type.STRING, 0);
            WindowTracker windowTracker = wrapper.user().get(WindowTracker.class);
            windowTracker.setInventory(inventory);
            windowTracker.setEntityId(entityId);
            if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                wrapper.set(Type.UNSIGNED_BYTE, 1, (short)17);
            }
        });
    }
}
