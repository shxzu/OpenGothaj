package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.google.common.primitives.Longs;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.NonceStorage;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_0;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.concurrent.ThreadLocalRandom;

class Protocol1_18_2To1_19$10
extends PacketHandlers {
    Protocol1_18_2To1_19$10() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE_ARRAY_PRIMITIVE);
        this.handler(wrapper -> {
            ChatSession1_19_0 chatSession = wrapper.user().get(ChatSession1_19_0.class);
            byte[] verifyToken = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            wrapper.write(Type.BOOLEAN, chatSession == null);
            if (chatSession != null) {
                long salt = ThreadLocalRandom.current().nextLong();
                byte[] signature = chatSession.sign(signer -> {
                    signer.accept(wrapper.user().remove(NonceStorage.class).nonce());
                    signer.accept(Longs.toByteArray(salt));
                });
                wrapper.write(Type.LONG, salt);
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
            } else {
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, verifyToken);
            }
        });
    }
}
