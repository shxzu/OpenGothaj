package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.ComponentUtil;
import java.nio.charset.StandardCharsets;

class PlayerPackets$31
extends PacketHandlers {
    PlayerPackets$31() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.read(Type.SHORT);
        this.handler(packetWrapper -> {
            String channel;
            switch (channel = packetWrapper.get(Type.STRING, 0)) {
                case "MC|TrSel": {
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.read(Type.REMAINING_BYTES);
                    break;
                }
                case "MC|ItemName": {
                    byte[] data = packetWrapper.read(Type.REMAINING_BYTES);
                    String name = new String(data, StandardCharsets.UTF_8);
                    packetWrapper.write(Type.STRING, name);
                    InventoryTracker windowTracker = packetWrapper.user().get(InventoryTracker.class);
                    PacketWrapper updateCost = PacketWrapper.create(49, null, packetWrapper.user());
                    updateCost.write(Type.UNSIGNED_BYTE, windowTracker.anvilId);
                    updateCost.write(Type.SHORT, (short)0);
                    updateCost.write(Type.SHORT, windowTracker.levelCost);
                    PacketUtil.sendPacket(updateCost, Protocol1_7_6_10To1_8.class, true, true);
                    break;
                }
                case "MC|BEdit": 
                case "MC|BSign": {
                    Item book = packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                    CompoundTag tag = book.tag();
                    if (tag != null && tag.contains("pages")) {
                        ListTag pages = (ListTag)tag.get("pages");
                        for (int i = 0; i < pages.size(); ++i) {
                            StringTag page = (StringTag)pages.get(i);
                            String value = page.getValue();
                            value = ComponentUtil.legacyToJsonString(value);
                            page.setValue(value);
                        }
                    }
                    packetWrapper.write(Type.ITEM1_8, book);
                    break;
                }
                case "MC|Brand": {
                    packetWrapper.write(Type.STRING, new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
                }
            }
        });
    }
}
