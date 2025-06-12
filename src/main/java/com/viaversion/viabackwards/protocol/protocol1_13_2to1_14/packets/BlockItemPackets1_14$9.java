package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_14$9
extends PacketHandlers {
    BlockItemPackets1_14$9() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14, Type.POSITION1_8);
    }
}
