package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_16_4To1_17$1
extends PacketHandlers {
    Protocol1_16_4To1_17$1() {
    }

    @Override
    public void register() {
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(wrapper -> wrapper.write(Type.INT, wrapper.read(Type.VAR_INT)));
    }
}
