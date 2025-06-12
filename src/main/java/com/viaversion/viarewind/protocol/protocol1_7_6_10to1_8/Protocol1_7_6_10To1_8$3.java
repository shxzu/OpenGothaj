package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_7_6_10To1_8$3
extends PacketHandlers {
    Protocol1_7_6_10To1_8$3() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
    }
}
