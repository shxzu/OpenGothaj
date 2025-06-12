package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;

class BlockItemPackets1_19_4$2
extends RecipeRewriter1_19_3<ClientboundPackets1_19_4> {
    BlockItemPackets1_19_4$2(Protocol arg0) {
        super(arg0);
    }

    @Override
    public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
        int ingredients = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
        wrapper.passthrough(Type.STRING);
        wrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < ingredients; ++i) {
            this.handleIngredient(wrapper);
        }
        this.rewrite(wrapper.passthrough(Type.ITEM1_13_2));
        wrapper.read(Type.BOOLEAN);
    }
}
