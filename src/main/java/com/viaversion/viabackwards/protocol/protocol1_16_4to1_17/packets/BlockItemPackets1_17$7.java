package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.MapColorRewrites;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_17$7
extends PacketHandlers {
    BlockItemPackets1_17$7() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.handler(wrapper -> wrapper.write(Type.BOOLEAN, true));
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            boolean hasMarkers = wrapper.read(Type.BOOLEAN);
            if (!hasMarkers) {
                wrapper.write(Type.VAR_INT, 0);
            } else {
                MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(wrapper);
            }
        });
    }
}
