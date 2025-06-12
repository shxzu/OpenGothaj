package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonParser;

class InventoryPackets$2
extends PacketHandlers {
    InventoryPackets$2() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.map(Type.COMPONENT);
        this.map(Type.UNSIGNED_BYTE);
        this.handler(packetWrapper -> {
            String type = packetWrapper.get(Type.STRING, 0);
            if (type.equals("EntityHorse")) {
                packetWrapper.passthrough(Type.INT);
            }
        });
        this.handler(packetWrapper -> {
            short windowId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            String windowType = packetWrapper.get(Type.STRING, 0);
            packetWrapper.user().get(Windows.class).put(windowId, windowType);
        });
        this.handler(packetWrapper -> {
            String name;
            String type = packetWrapper.get(Type.STRING, 0);
            if (type.equalsIgnoreCase("minecraft:shulker_box")) {
                type = "minecraft:container";
                packetWrapper.set(Type.STRING, 0, "minecraft:container");
            }
            if ((name = packetWrapper.get(Type.COMPONENT, 0).toString()).equalsIgnoreCase("{\"translate\":\"container.shulkerBox\"}")) {
                packetWrapper.set(Type.COMPONENT, 0, JsonParser.parseString("{\"text\":\"Shulker Box\"}"));
            }
        });
    }
}
