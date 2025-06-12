package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatTypeStorage1_19_3;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.Protocol1_19To1_19_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.util.ComponentUtil;

class Protocol1_19_1To1_19_3$7
extends PacketHandlers {
    Protocol1_19_1To1_19_3$7() {
    }

    @Override
    public void register() {
        this.read(Type.UUID);
        this.read(Type.VAR_INT);
        this.read(OPTIONAL_SIGNATURE_BYTES_TYPE);
        this.handler(wrapper -> {
            String plainContent = wrapper.read(Type.STRING);
            wrapper.read(Type.LONG);
            wrapper.read(Type.LONG);
            int lastSeen = wrapper.read(Type.VAR_INT);
            for (int i = 0; i < lastSeen; ++i) {
                int index = wrapper.read(Type.VAR_INT);
                if (index != 0) continue;
                wrapper.read(SIGNATURE_BYTES_TYPE);
            }
            JsonElement unsignedContent = wrapper.read(Type.OPTIONAL_COMPONENT);
            JsonElement content = unsignedContent != null ? unsignedContent : ComponentUtil.plainToJson(plainContent);
            Protocol1_19_1To1_19_3.this.translatableRewriter.processText(content);
            int filterMaskType = wrapper.read(Type.VAR_INT);
            if (filterMaskType == 2) {
                wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
            }
            int chatTypeId = wrapper.read(Type.VAR_INT);
            JsonElement senderName = wrapper.read(Type.COMPONENT);
            JsonElement targetName = wrapper.read(Type.OPTIONAL_COMPONENT);
            JsonElement result = Protocol1_19To1_19_1.decorateChatMessage(wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
            if (result == null) {
                wrapper.cancel();
                return;
            }
            wrapper.write(Type.COMPONENT, result);
            wrapper.write(Type.BOOLEAN, false);
        });
    }
}
