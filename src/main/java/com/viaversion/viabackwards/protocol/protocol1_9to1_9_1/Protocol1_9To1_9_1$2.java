package com.viaversion.viabackwards.protocol.protocol1_9to1_9_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9To1_9_1$2
extends PacketHandlers {
    Protocol1_9To1_9_1$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int sound = wrapper.get(Type.VAR_INT, 0);
            if (sound == 415) {
                wrapper.cancel();
            } else if (sound >= 416) {
                wrapper.set(Type.VAR_INT, 0, sound - 1);
            }
        });
    }
}
