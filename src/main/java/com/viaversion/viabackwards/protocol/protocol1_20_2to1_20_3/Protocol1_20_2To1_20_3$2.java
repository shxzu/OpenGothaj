package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_20_2To1_20_3$2
extends PacketHandlers {
    Protocol1_20_2To1_20_3$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> Protocol1_20_2To1_20_3.this.convertComponent(wrapper));
    }
}
