package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_17_1To1_18$3
extends PacketHandlers {
    Protocol1_17_1To1_18$3() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE);
        this.map(Type.STRING);
        this.handler(Protocol1_17_1To1_18.this.cutName(0, 16));
    }
}
