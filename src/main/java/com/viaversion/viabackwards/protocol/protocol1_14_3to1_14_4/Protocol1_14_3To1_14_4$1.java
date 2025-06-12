package com.viaversion.viabackwards.protocol.protocol1_14_3to1_14_4;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_14_3To1_14_4$1
extends PacketHandlers {
    Protocol1_14_3To1_14_4$1() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int status = wrapper.read(Type.VAR_INT);
            boolean allGood = wrapper.read(Type.BOOLEAN);
            if (allGood && status == 0) {
                wrapper.cancel();
            }
        });
    }
}
