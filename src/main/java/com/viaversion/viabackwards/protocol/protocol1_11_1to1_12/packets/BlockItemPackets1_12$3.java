package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;

class BlockItemPackets1_12$3
extends PacketHandlers {
    BlockItemPackets1_12$3() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.VAR_INT);
        this.map(Type.ITEM1_8);
        this.handler(wrapper -> {
            if (wrapper.get(Type.VAR_INT, 0) == 1) {
                wrapper.set(Type.ITEM1_8, 0, null);
                PacketWrapper confirm = wrapper.create(ServerboundPackets1_12.WINDOW_CONFIRMATION);
                confirm.write(Type.UNSIGNED_BYTE, wrapper.get(Type.UNSIGNED_BYTE, 0));
                confirm.write(Type.SHORT, wrapper.get(Type.SHORT, 1));
                confirm.write(Type.BOOLEAN, false);
                wrapper.sendToServer(Protocol1_11_1To1_12.class);
                wrapper.cancel();
                confirm.sendToServer(Protocol1_11_1To1_12.class);
                return;
            }
            Item item = wrapper.get(Type.ITEM1_8, 0);
            BlockItemPackets1_12.this.handleItemToServer(item);
        });
    }
}
