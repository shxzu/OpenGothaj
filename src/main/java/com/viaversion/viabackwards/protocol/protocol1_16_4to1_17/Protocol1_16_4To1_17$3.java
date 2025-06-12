package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_16_4To1_17$3
extends PacketHandlers {
    Protocol1_16_4To1_17$3() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> wrapper.write(Type.BOOLEAN, false));
    }
}
