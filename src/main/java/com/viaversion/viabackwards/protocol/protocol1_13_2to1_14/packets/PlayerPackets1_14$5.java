package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_14$5
extends PacketHandlers {
    PlayerPackets1_14$5() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int type = wrapper.get(Type.VAR_INT, 0);
            if (type == 0) {
                wrapper.passthrough(Type.STRING);
            } else if (type == 1) {
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.write(Type.BOOLEAN, false);
                wrapper.write(Type.BOOLEAN, false);
                wrapper.write(Type.BOOLEAN, false);
                wrapper.write(Type.BOOLEAN, false);
            }
        });
    }
}
