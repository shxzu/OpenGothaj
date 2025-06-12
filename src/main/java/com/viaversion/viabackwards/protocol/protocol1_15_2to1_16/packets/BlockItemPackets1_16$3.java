package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewrites;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_16$3
extends PacketHandlers {
    BlockItemPackets1_16$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
    }
}
