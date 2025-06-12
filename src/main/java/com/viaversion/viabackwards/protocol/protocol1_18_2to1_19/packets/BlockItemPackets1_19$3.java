package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19$3
extends PacketHandlers {
    BlockItemPackets1_19$3() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
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
            ParticleMappings particleMappings;
            int id = wrapper.get(Type.INT, 0);
            if (id == (particleMappings = ((Protocol1_18_2To1_19)BlockItemPackets1_19.this.protocol).getMappingData().getParticleMappings()).id("sculk_charge")) {
                wrapper.set(Type.INT, 0, -1);
                wrapper.cancel();
            } else if (id == particleMappings.id("shriek")) {
                wrapper.set(Type.INT, 0, -1);
                wrapper.cancel();
            } else if (id == particleMappings.id("vibration")) {
                wrapper.set(Type.INT, 0, -1);
                wrapper.cancel();
            }
        });
        this.handler(BlockItemPackets1_19.this.getSpawnParticleHandler());
    }
}
