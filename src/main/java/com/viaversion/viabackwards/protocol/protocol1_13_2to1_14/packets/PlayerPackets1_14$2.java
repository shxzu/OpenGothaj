package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_14$2
extends PacketHandlers {
    PlayerPackets1_14$2() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14, Type.POSITION1_8);
    }
}
