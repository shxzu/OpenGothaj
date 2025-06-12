package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;

class EntityPackets1_11_1$2
extends PacketHandlers {
    EntityPackets1_11_1$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Types1_9.METADATA_LIST);
        this.handler(EntityPackets1_11_1.this.getTrackerHandler());
        this.handler(EntityPackets1_11_1.this.getMobSpawnRewriter(Types1_9.METADATA_LIST));
    }
}
