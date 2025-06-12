package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.NonceStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19_1To1_19_3$4
extends PacketHandlers {
    Protocol1_19_1To1_19_3$4() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE_ARRAY_PRIMITIVE);
        this.handler(wrapper -> {
            NonceStorage nonceStorage = wrapper.user().remove(NonceStorage.class);
            boolean isNonce = wrapper.read(Type.BOOLEAN);
            if (!isNonce) {
                wrapper.read(Type.LONG);
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce() != null ? nonceStorage.nonce() : new byte[]{});
            }
        });
    }
}
