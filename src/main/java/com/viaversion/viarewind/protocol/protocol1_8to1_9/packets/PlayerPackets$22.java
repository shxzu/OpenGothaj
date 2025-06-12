package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;

class PlayerPackets$22
extends PacketHandlers {
    PlayerPackets$22() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String channel = packetWrapper.get(Type.STRING, 0);
            if (channel.equalsIgnoreCase("MC|BEdit") || channel.equalsIgnoreCase("MC|BSign")) {
                Item book = packetWrapper.passthrough(Type.ITEM);
                book.setIdentifier(386);
                CompoundTag tag = book.tag();
                if (tag.contains("pages")) {
                    ListTag pages = (ListTag)tag.get("pages");
                    if (pages.size() > ViaRewind.getConfig().getMaxBookPages()) {
                        packetWrapper.user().disconnect("Too many book pages");
                        return;
                    }
                    for (int i = 0; i < pages.size(); ++i) {
                        StringTag page = (StringTag)pages.get(i);
                        String value = page.getValue();
                        if (value.length() > ViaRewind.getConfig().getMaxBookPageSize()) {
                            packetWrapper.user().disconnect("Book page too large");
                            return;
                        }
                        value = ChatUtil.jsonToLegacy(value);
                        page.setValue(value);
                    }
                }
            } else if (channel.equalsIgnoreCase("MC|AdvCdm")) {
                channel = "MC|AdvCmd";
                packetWrapper.set(Type.STRING, 0, "MC|AdvCmd");
            }
        });
    }
}
