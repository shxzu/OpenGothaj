package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$2
extends PacketHandlers {
    EntityPackets$2() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map(Type.POSITION1_8, Types1_7_6_10.U_BYTE_POSITION);
    }
}
