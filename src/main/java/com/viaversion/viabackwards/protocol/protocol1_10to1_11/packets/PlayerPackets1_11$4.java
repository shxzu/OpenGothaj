package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_11$4
extends PacketHandlers {
    PlayerPackets1_11$4() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.UNSIGNED_BYTE, TO_NEW_FLOAT);
        this.map(Type.UNSIGNED_BYTE, TO_NEW_FLOAT);
        this.map(Type.UNSIGNED_BYTE, TO_NEW_FLOAT);
    }
}
