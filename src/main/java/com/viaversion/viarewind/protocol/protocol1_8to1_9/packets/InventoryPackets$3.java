package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$3
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    InventoryPackets$3(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.handler(packetWrapper -> {
            short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            Item[] items = packetWrapper.read(Type.ITEM1_8_SHORT_ARRAY);
            for (int i = 0; i < items.length; ++i) {
                items[i] = this.val$protocol.getItemRewriter().handleItemToClient(items[i]);
            }
            if (windowId == 0 && items.length == 46) {
                Item[] old = items;
                items = new Item[45];
                System.arraycopy(old, 0, items, 0, 45);
            } else {
                String type = packetWrapper.user().get(Windows.class).get(windowId);
                if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
                    System.arraycopy(items, 0, packetWrapper.user().get(Windows.class).getBrewingItems(windowId), 0, 4);
                    Windows.updateBrewingStand(packetWrapper.user(), items[4], windowId);
                    Item[] old = items;
                    items = new Item[old.length - 1];
                    System.arraycopy(old, 0, items, 0, 4);
                    System.arraycopy(old, 5, items, 4, old.length - 5);
                }
            }
            packetWrapper.write(Type.ITEM1_8_SHORT_ARRAY, items);
        });
    }
}
