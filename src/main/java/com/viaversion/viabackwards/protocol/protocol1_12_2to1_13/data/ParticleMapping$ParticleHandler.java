package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import java.util.List;

public interface ParticleMapping$ParticleHandler {
    public int[] rewrite(Protocol1_12_2To1_13 var1, PacketWrapper var2) throws Exception;

    public int[] rewrite(Protocol1_12_2To1_13 var1, List<Particle.ParticleData<?>> var2);

    default public boolean isBlockHandler() {
        return false;
    }
}
