package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$7
extends PacketHandlers {
    PlayerPackets$7() {
    }

    @Override
    public void register() {
        this.map(Type.FLOAT);
        this.map((Type)Type.VAR_INT, Type.SHORT);
        this.map((Type)Type.VAR_INT, Type.SHORT);
    }
}
