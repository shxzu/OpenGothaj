package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SoundPackets1_12$1
extends PacketHandlers {
    SoundPackets1_12$1() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.VAR_INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
    }
}
