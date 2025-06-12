package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$3
extends PacketHandlers {
    EntityPackets$3() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map((Type)Type.VAR_INT, Type.INT);
        this.handler(wrapper -> wrapper.user().get(EntityTracker1_7_6_10.class).removeEntity(wrapper.get(Type.INT, 0)));
    }
}
