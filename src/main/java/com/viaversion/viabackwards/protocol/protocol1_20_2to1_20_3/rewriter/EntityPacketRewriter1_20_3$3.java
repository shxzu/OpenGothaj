package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPacketRewriter1_20_3$3
extends PacketHandlers {
    EntityPacketRewriter1_20_3$3() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.STRING);
        this.handler(EntityPacketRewriter1_20_3.this.spawnPositionHandler());
        this.handler(EntityPacketRewriter1_20_3.this.worldDataTrackerHandlerByKey());
    }
}
