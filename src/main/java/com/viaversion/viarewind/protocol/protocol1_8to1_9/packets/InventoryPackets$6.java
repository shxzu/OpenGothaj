package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$6
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    InventoryPackets$6(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map((Type)Type.BYTE, Type.VAR_INT);
        this.map(Type.ITEM1_8);
        this.handler(packetWrapper -> packetWrapper.set(Type.ITEM1_8, 0, this.val$protocol.getItemRewriter().handleItemToServer(packetWrapper.get(Type.ITEM1_8, 0))));
        this.handler(packetWrapper -> {
            short slot;
            short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            Windows windows = packetWrapper.user().get(Windows.class);
            String type = windows.get(windowId);
            if (type == null) {
                return;
            }
            if (type.equalsIgnoreCase("minecraft:brewing_stand") && (slot = packetWrapper.get(Type.SHORT, 0).shortValue()) > 3) {
                slot = (short)(slot + 1);
                packetWrapper.set(Type.SHORT, 0, slot);
            }
        });
    }
}
