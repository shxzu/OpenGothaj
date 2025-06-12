package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.List;

class ParticleMapping$2
implements ParticleMapping.ParticleHandler {
    ParticleMapping$2() {
    }

    @Override
    public int[] rewrite(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
        float r = wrapper.read(Type.FLOAT).floatValue();
        float g = wrapper.read(Type.FLOAT).floatValue();
        float b = wrapper.read(Type.FLOAT).floatValue();
        float scale = wrapper.read(Type.FLOAT).floatValue();
        wrapper.set(Type.FLOAT, 3, Float.valueOf(r));
        wrapper.set(Type.FLOAT, 4, Float.valueOf(g));
        wrapper.set(Type.FLOAT, 5, Float.valueOf(b));
        wrapper.set(Type.FLOAT, 6, Float.valueOf(scale));
        wrapper.set(Type.INT, 1, 0);
        return null;
    }

    @Override
    public int[] rewrite(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData<?>> data) {
        return null;
    }
}
