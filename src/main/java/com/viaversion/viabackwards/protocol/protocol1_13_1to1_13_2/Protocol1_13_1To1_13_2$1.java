package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_13_1To1_13_2$1
extends PacketHandlers {
    Protocol1_13_1To1_13_2$1() {
    }

    @Override
    public void register() {
        this.map(Type.ITEM1_13, Type.ITEM1_13_2);
    }
}
