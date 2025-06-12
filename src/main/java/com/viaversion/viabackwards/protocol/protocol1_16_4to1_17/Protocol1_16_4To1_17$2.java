package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_16_4To1_17$2
extends PacketHandlers {
    Protocol1_16_4To1_17$2() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14);
        this.handler(wrapper -> wrapper.read(Type.FLOAT));
    }
}
