package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$4
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    InventoryPackets$4(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.ITEM1_8);
        this.handler(packetWrapper -> {
            packetWrapper.set(Type.ITEM1_8, 0, this.val$protocol.getItemRewriter().handleItemToClient(packetWrapper.get(Type.ITEM1_8, 0)));
            byte windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0).byteValue();
            short slot = packetWrapper.get(Type.SHORT, 0);
            if (windowId == 0 && slot == 45) {
                packetWrapper.cancel();
                return;
            }
            String type = packetWrapper.user().get(Windows.class).get(windowId);
            if (type == null) {
                return;
            }
            if (type.equalsIgnoreCase("minecraft:brewing_stand")) {
                if (slot > 4) {
                    slot = (short)(slot - 1);
                    packetWrapper.set(Type.SHORT, 0, slot);
                } else if (slot == 4) {
                    packetWrapper.cancel();
                    Windows.updateBrewingStand(packetWrapper.user(), packetWrapper.get(Type.ITEM1_8, 0), windowId);
                } else {
                    packetWrapper.user().get(Windows.class).getBrewingItems((short)((short)windowId))[slot] = packetWrapper.get(Type.ITEM1_8, 0);
                }
            }
        });
    }
}
