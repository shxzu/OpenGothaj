package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19_1To1_19_3$1
extends PacketHandlers {
    Protocol1_19_1To1_19_3$1() {
    }

    @Override
    public void register() {
        this.map(Type.OPTIONAL_COMPONENT);
        this.map(Type.OPTIONAL_STRING);
        this.create(Type.BOOLEAN, false);
    }
}
