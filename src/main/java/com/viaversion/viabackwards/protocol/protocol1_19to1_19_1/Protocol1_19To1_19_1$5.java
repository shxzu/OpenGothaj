package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.NonceStorage;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19To1_19_1$5
extends PacketHandlers {
    Protocol1_19To1_19_1$5() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            ProfileKey profileKey = wrapper.read(Type.OPTIONAL_PROFILE_KEY);
            ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
            wrapper.write(Type.OPTIONAL_PROFILE_KEY, chatSession == null ? null : chatSession.getProfileKey());
            wrapper.write(Type.OPTIONAL_UUID, chatSession == null ? null : chatSession.getUuid());
            if (profileKey == null || chatSession != null) {
                wrapper.user().put(new NonceStorage(null));
            }
        });
    }
}
