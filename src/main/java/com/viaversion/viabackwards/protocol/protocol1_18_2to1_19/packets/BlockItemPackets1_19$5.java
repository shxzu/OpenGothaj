package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19$5
extends PacketHandlers {
    BlockItemPackets1_19$5() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.POSITION1_14);
        this.map(Type.VAR_INT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.BOOLEAN);
        this.create(Type.VAR_INT, 0);
    }
}
