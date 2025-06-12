package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_15_2To1_16$2
extends PacketHandlers {
    Protocol1_15_2To1_16$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> Protocol1_15_2To1_16.this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT)));
        this.handler(wrapper -> {
            int windowType = wrapper.get(Type.VAR_INT, 1);
            if (windowType == 20) {
                wrapper.set(Type.VAR_INT, 1, 7);
            } else if (windowType > 20) {
                wrapper.set(Type.VAR_INT, 1, --windowType);
            }
        });
    }
}
