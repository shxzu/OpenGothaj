package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.ParticleIndex1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$6
extends PacketHandlers {
    WorldPackets$6() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            int particleId = wrapper.read(Type.INT);
            ParticleIndex1_7_6_10 particle = ParticleIndex1_7_6_10.find(particleId);
            if (particle == null) {
                particle = ParticleIndex1_7_6_10.CRIT;
            }
            wrapper.write(Type.STRING, particle.name);
        });
        this.read(Type.BOOLEAN);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            String name = wrapper.get(Type.STRING, 0);
            ParticleIndex1_7_6_10 particle = ParticleIndex1_7_6_10.find(name);
            if (particle == ParticleIndex1_7_6_10.ICON_CRACK || particle == ParticleIndex1_7_6_10.BLOCK_CRACK || particle == ParticleIndex1_7_6_10.BLOCK_DUST) {
                int data;
                int id = wrapper.read(Type.VAR_INT);
                int n = data = particle == ParticleIndex1_7_6_10.ICON_CRACK ? wrapper.read(Type.VAR_INT) : id / 4096;
                if ((id %= 4096) >= 256 && id <= 422 || id >= 2256 && id <= 2267) {
                    particle = ParticleIndex1_7_6_10.ICON_CRACK;
                } else if (id >= 0 && id <= 164 || id >= 170 && id <= 175) {
                    if (particle == ParticleIndex1_7_6_10.ICON_CRACK) {
                        particle = ParticleIndex1_7_6_10.BLOCK_CRACK;
                    }
                } else {
                    wrapper.cancel();
                    return;
                }
                name = particle.name + "_" + id + "_" + data;
            }
            wrapper.set(Type.STRING, 0, name);
        });
    }
}
