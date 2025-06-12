package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ChatDecorationResult;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.Protocol1_19_1To1_19;
import java.util.UUID;

class Protocol1_18_2To1_19$4
extends PacketHandlers {
    Protocol1_18_2To1_19$4() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            JsonElement signedContent = wrapper.read(Type.COMPONENT);
            JsonElement unsignedContent = wrapper.read(Type.OPTIONAL_COMPONENT);
            int chatTypeId = wrapper.read(Type.VAR_INT);
            UUID sender = wrapper.read(Type.UUID);
            JsonElement senderName = wrapper.read(Type.COMPONENT);
            JsonElement teamName = wrapper.read(Type.OPTIONAL_COMPONENT);
            CompoundTag chatType = wrapper.user().get(DimensionRegistryStorage.class).chatType(chatTypeId);
            ChatDecorationResult decorationResult = Protocol1_19_1To1_19.decorateChatMessage(chatType, chatTypeId, senderName, teamName, unsignedContent != null ? unsignedContent : signedContent);
            if (decorationResult == null) {
                wrapper.cancel();
                return;
            }
            Protocol1_18_2To1_19.this.translatableRewriter.processText(decorationResult.content());
            wrapper.write(Type.COMPONENT, decorationResult.content());
            wrapper.write(Type.BYTE, decorationResult.overlay() ? (byte)2 : (byte)1);
            wrapper.write(Type.UUID, sender);
        });
        this.read(Type.LONG);
        this.read(Type.LONG);
        this.read(Type.BYTE_ARRAY_PRIMITIVE);
    }
}
