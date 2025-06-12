package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatTypeStorage1_19_3;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_3;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;

class EntityPackets1_19_3$1
extends PacketHandlers {
    EntityPackets1_19_3$1() {
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
        this.handler(EntityPackets1_19_3.this.dimensionDataHandler());
        this.handler(EntityPackets1_19_3.this.biomeSizeTracker());
        this.handler(EntityPackets1_19_3.this.worldDataTrackerHandlerByKey());
        this.handler(wrapper -> {
            ChatTypeStorage1_19_3 chatTypeStorage = wrapper.user().get(ChatTypeStorage1_19_3.class);
            chatTypeStorage.clear();
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
            for (Tag chatType : chatTypes) {
                CompoundTag chatTypeCompound = (CompoundTag)chatType;
                NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
                chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
            }
        });
        this.handler(wrapper -> {
            ChatSession1_19_3 chatSession = wrapper.user().get(ChatSession1_19_3.class);
            if (chatSession != null) {
                PacketWrapper chatSessionUpdate = wrapper.create(ServerboundPackets1_19_3.CHAT_SESSION_UPDATE);
                chatSessionUpdate.write(Type.UUID, chatSession.getSessionId());
                chatSessionUpdate.write(Type.PROFILE_KEY, chatSession.getProfileKey());
                chatSessionUpdate.sendToServer(Protocol1_19_1To1_19_3.class);
            }
        });
    }
}
