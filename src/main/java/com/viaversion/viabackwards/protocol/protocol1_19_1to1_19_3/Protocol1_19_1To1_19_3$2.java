package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.NonceStorage;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19_1To1_19_3$2
extends PacketHandlers {
    Protocol1_19_1To1_19_3$2() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            ProfileKey profileKey = wrapper.read(Type.OPTIONAL_PROFILE_KEY);
            if (profileKey == null) {
                wrapper.user().put(new NonceStorage(null));
            }
        });
    }
}
