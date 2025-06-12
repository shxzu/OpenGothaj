package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPacketRewriter1_20_3$1
extends PacketHandlers {
    EntityPacketRewriter1_20_3$1() {
    }

    @Override
    protected void register() {
        this.map(Type.COMPOUND_TAG);
        this.handler(EntityPacketRewriter1_20_3.this.configurationDimensionDataHandler());
        this.handler(EntityPacketRewriter1_20_3.this.configurationBiomeSizeTracker());
    }
}
