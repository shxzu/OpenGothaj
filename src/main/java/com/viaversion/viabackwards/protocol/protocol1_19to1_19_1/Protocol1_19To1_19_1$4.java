package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_19To1_19_1$4
extends PacketHandlers {
    Protocol1_19To1_19_1$4() {
    }

    @Override
    public void register() {
        this.map(Type.OPTIONAL_COMPONENT);
        this.map(Type.OPTIONAL_STRING);
        this.map(Type.BOOLEAN);
        this.read(Type.BOOLEAN);
    }
}
