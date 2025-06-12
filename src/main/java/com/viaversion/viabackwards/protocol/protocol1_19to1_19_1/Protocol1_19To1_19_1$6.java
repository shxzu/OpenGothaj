package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.NonceStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.CipherUtil;

class Protocol1_19To1_19_1$6
extends PacketHandlers {
    Protocol1_19To1_19_1$6() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            if (wrapper.user().has(NonceStorage.class)) {
                return;
            }
            byte[] publicKey = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
            byte[] nonce = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
            wrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
        });
    }
}
