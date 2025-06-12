package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$5
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    InventoryPackets$5(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.SHORT);
        this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM1_8);
        this.handler(wrapper -> {
            Item item = wrapper.get(Type.ITEM1_8, 0);
            this.val$protocol.getItemRewriter().handleItemToServer(item);
            wrapper.set(Type.ITEM1_8, 0, item);
        });
    }
}
