package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage1_19_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.EntityPackets;

class Protocol1_19To1_19_1$1
extends PacketHandlers {
    Protocol1_19To1_19_1$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.STRING_ARRAY);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.map(Type.STRING);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            ChatRegistryStorage chatTypeStorage = wrapper.user().get(ChatRegistryStorage1_19_1.class);
            chatTypeStorage.clear();
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
            for (Tag chatType : chatTypes) {
                CompoundTag chatTypeCompound = (CompoundTag)chatType;
                NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
                chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
            }
            registry.put("minecraft:chat_type", EntityPackets.CHAT_REGISTRY.copy());
        });
        this.handler(Protocol1_19To1_19_1.this.entityRewriter.worldTrackerHandlerByKey());
    }
}
