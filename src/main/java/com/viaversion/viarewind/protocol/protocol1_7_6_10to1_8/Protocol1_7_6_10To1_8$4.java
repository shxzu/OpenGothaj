package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_7_6_10To1_8$4
extends PacketHandlers {
    Protocol1_7_6_10To1_8$4() {
    }

    @Override
    public void register() {
        this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
        this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
    }
}
