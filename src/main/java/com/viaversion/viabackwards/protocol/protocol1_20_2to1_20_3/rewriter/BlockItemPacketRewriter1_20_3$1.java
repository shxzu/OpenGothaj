package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.Protocol1_20_2To1_20_3;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPacketRewriter1_20_3$1
extends PacketHandlers {
    BlockItemPacketRewriter1_20_3$1() {
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
            ParticleMappings particleMappings;
            int id = wrapper.get(Type.VAR_INT, 0);
            if (id == (particleMappings = ((Protocol1_20_2To1_20_3)BlockItemPacketRewriter1_20_3.this.protocol).getMappingData().getParticleMappings()).id("vibration")) {
                int positionSourceType = wrapper.read(Type.VAR_INT);
                if (positionSourceType == 0) {
                    wrapper.write(Type.STRING, "minecraft:block");
                } else if (positionSourceType == 1) {
                    wrapper.write(Type.STRING, "minecraft:entity");
                } else {
                    ViaBackwards.getPlatform().getLogger().warning("Unknown position source type: " + positionSourceType);
                    wrapper.cancel();
                }
            }
        });
        this.handler(BlockItemPacketRewriter1_20_3.this.getSpawnParticleHandler(Type.VAR_INT));
    }
}
