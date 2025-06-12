package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.rewriter.RecipeRewriter1_20_3;

class BlockItemPacketRewriter1_20_3$2
extends RecipeRewriter1_20_3<ClientboundPackets1_20_3> {
    BlockItemPacketRewriter1_20_3$2(Protocol arg0) {
        super(arg0);
    }

    @Override
    public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
        String group = wrapper.read(Type.STRING);
        int craftingBookCategory = wrapper.read(Type.VAR_INT);
        int width = wrapper.passthrough(Type.VAR_INT);
        int height = wrapper.passthrough(Type.VAR_INT);
        wrapper.write(Type.STRING, group);
        wrapper.write(Type.VAR_INT, craftingBookCategory);
        int ingredients = height * width;
        for (int i = 0; i < ingredients; ++i) {
            this.handleIngredient(wrapper);
        }
        this.rewrite(wrapper.passthrough(this.itemType()));
        wrapper.passthrough(Type.BOOLEAN);
    }
}
