package com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_7_2_5To1_7_6_10$2
extends PacketHandlers {
    Protocol1_7_2_5To1_7_6_10$2() {
    }

    @Override
    public void register() {
        this.map(Type.STRING, REMOVE_DASHES);
        this.map(Type.STRING);
    }
}
