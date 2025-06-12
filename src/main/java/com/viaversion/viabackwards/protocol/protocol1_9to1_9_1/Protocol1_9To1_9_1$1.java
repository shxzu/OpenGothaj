package com.viaversion.viabackwards.protocol.protocol1_9to1_9_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9To1_9_1$1
extends PacketHandlers {
    Protocol1_9To1_9_1$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map((Type)Type.INT, Type.BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.map(Type.BOOLEAN);
    }
}
