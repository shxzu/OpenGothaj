package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_13To1_13_1$5
extends PacketHandlers {
    Protocol1_13To1_13_1$5() {
    }

    @Override
    public void register() {
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int action = wrapper.get(Type.VAR_INT, 0);
            if (action == 0 || action == 3) {
                Protocol1_13To1_13_1.this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT));
                if (action == 0) {
                    wrapper.passthrough(Type.FLOAT);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.VAR_INT);
                    short flags = wrapper.read(Type.UNSIGNED_BYTE);
                    if ((flags & 4) != 0) {
                        flags = (short)(flags | 2);
                    }
                    wrapper.write(Type.UNSIGNED_BYTE, flags);
                }
            }
        });
    }
}
