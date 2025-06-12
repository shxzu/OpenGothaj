package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_17_1To1_18$1
extends PacketHandlers {
    Protocol1_17_1To1_18$1() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.create(Type.BOOLEAN, true);
    }
}
