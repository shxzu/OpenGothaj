package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19_3$1
extends PacketHandlers {
    BlockItemPackets1_19_3$1() {
    }

    @Override
    public void register() {
        this.map((Type)Type.DOUBLE, Type.FLOAT);
        this.map((Type)Type.DOUBLE, Type.FLOAT);
        this.map((Type)Type.DOUBLE, Type.FLOAT);
    }
}
