package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPacketRewriter1_20_2$6
extends PacketHandlers {
    BlockItemPacketRewriter1_20_2$6() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.VAR_INT, 0);
            ParticleMappings mappings = Protocol1_20To1_20_2.MAPPINGS.getParticleMappings();
            if (mappings.isBlockParticle(id)) {
                int data = wrapper.read(Type.VAR_INT);
                wrapper.write(Type.VAR_INT, ((Protocol1_20To1_20_2)BlockItemPacketRewriter1_20_2.this.protocol).getMappingData().getNewBlockStateId(data));
            } else if (mappings.isItemParticle(id)) {
                wrapper.write(Type.ITEM1_13_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
            }
        });
    }
}
