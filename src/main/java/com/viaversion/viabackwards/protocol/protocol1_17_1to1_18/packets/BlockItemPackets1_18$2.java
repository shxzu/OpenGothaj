package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_18$2
extends PacketHandlers {
    BlockItemPackets1_18$2() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
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
            int id = wrapper.get(Type.INT, 0);
            if (id == 3) {
                int blockState = wrapper.read(Type.VAR_INT);
                if (blockState == 7786) {
                    wrapper.set(Type.INT, 0, 3);
                } else {
                    wrapper.set(Type.INT, 0, 2);
                }
                return;
            }
            ParticleMappings mappings = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getParticleMappings();
            if (mappings.isBlockParticle(id)) {
                int data = wrapper.passthrough(Type.VAR_INT);
                wrapper.set(Type.VAR_INT, 0, ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewBlockStateId(data));
            } else if (mappings.isItemParticle(id)) {
                BlockItemPackets1_18.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
            }
            int newId = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewParticleId(id);
            if (newId != id) {
                wrapper.set(Type.INT, 0, newId);
            }
        });
    }
}
