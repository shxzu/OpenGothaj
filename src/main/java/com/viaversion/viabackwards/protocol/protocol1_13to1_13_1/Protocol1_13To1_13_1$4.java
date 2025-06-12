package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_13To1_13_1$4
extends PacketHandlers {
    Protocol1_13To1_13_1$4() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int start = wrapper.get(Type.VAR_INT, 1);
            wrapper.set(Type.VAR_INT, 1, start - 1);
            int count = wrapper.get(Type.VAR_INT, 3);
            for (int i = 0; i < count; ++i) {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.OPTIONAL_COMPONENT);
            }
        });
    }
}
