package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.NonceStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19To1_19_1$7
extends PacketHandlers {
    Protocol1_19To1_19_1$7() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE_ARRAY_PRIMITIVE);
        this.handler(wrapper -> {
            NonceStorage nonceStorage = wrapper.user().remove(NonceStorage.class);
            if (nonceStorage.nonce() == null) {
                return;
            }
            boolean isNonce = wrapper.read(Type.BOOLEAN);
            wrapper.write(Type.BOOLEAN, true);
            if (!isNonce) {
                wrapper.read(Type.LONG);
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
            }
        });
    }
}
