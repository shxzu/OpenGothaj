package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9_4To1_10$4
extends PacketHandlers {
    Protocol1_9_4To1_10$4() {
    }

    @Override
    public void register() {
        this.read(Type.STRING);
        this.map(Type.VAR_INT);
    }
}
