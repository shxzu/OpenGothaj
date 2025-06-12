package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_20$2
extends PacketHandlers {
    EntityPackets1_20$2() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.BYTE);
        this.map(Type.OPTIONAL_GLOBAL_POSITION);
        this.read(Type.VAR_INT);
        this.handler(EntityPackets1_20.this.worldDataTrackerHandlerByKey());
    }
}
