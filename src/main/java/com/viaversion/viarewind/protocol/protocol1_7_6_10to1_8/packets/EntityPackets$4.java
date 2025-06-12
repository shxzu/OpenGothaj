package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$4
extends PacketHandlers {
    EntityPackets$4() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
    }
}
