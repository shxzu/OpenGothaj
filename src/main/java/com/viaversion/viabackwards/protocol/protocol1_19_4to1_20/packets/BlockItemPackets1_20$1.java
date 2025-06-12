package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.rewriter.BlockRewriter;

class BlockItemPackets1_20$1
extends PacketHandlers {
    final BlockRewriter val$blockRewriter;

    BlockItemPackets1_20$1(BlockRewriter blockRewriter) {
        this.val$blockRewriter = blockRewriter;
    }

    @Override
    protected void register() {
        this.handler(this.val$blockRewriter.chunkDataHandler1_19(ChunkType1_18::new, x$0 -> BlockItemPackets1_20.this.handleBlockEntity(x$0)));
        this.create(Type.BOOLEAN, true);
    }
}
