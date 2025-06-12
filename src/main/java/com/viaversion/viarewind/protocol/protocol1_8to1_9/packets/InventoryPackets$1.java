package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$1
extends PacketHandlers {
    InventoryPackets$1() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.handler(packetWrapper -> {
            short windowsId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            packetWrapper.user().get(Windows.class).remove(windowsId);
        });
    }
}
