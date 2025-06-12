package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_0;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_18_2To1_19$9
extends PacketHandlers {
    Protocol1_18_2To1_19$9() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            ChatSession1_19_0 chatSession = wrapper.user().get(ChatSession1_19_0.class);
            wrapper.write(Type.OPTIONAL_PROFILE_KEY, chatSession == null ? null : chatSession.getProfileKey());
        });
    }
}
