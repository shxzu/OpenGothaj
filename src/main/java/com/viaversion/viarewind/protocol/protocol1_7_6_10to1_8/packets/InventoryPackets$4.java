package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$4
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    InventoryPackets$4(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map((Type)Type.BYTE, Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.handler(wrapper -> {
            short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
            short slot = wrapper.get(Type.SHORT, 0);
            short windowType = wrapper.user().get(InventoryTracker.class).get(windowId);
            if (windowType == 4 && slot > 0) {
                wrapper.set(Type.SHORT, 0, (short)(slot + 1));
            }
        });
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM1_8);
        this.handler(wrapper -> {
            Item item = wrapper.get(Type.ITEM1_8, 0);
            this.val$protocol.getItemRewriter().handleItemToServer(item);
            wrapper.set(Type.ITEM1_8, 0, item);
        });
    }
}
