package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.NonceStorage;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_0;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_18_2To1_19$8
extends PacketHandlers {
    Protocol1_18_2To1_19$8() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE_ARRAY_PRIMITIVE);
        this.handler(wrapper -> {
            if (wrapper.user().has(ChatSession1_19_0.class)) {
                wrapper.user().put(new NonceStorage(wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE)));
            }
        });
    }
}
