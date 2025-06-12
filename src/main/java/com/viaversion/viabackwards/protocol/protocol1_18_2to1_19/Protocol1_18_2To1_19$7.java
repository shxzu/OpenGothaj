package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_18_2To1_19$7
extends PacketHandlers {
    Protocol1_18_2To1_19$7() {
    }

    @Override
    public void register() {
        this.map(Type.UUID);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            int properties = wrapper.read(Type.VAR_INT);
            for (int i = 0; i < properties; ++i) {
                wrapper.read(Type.STRING);
                wrapper.read(Type.STRING);
                if (!wrapper.read(Type.BOOLEAN).booleanValue()) continue;
                wrapper.read(Type.STRING);
            }
        });
    }
}
