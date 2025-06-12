package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.Particle;
import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface ParticleType$ParticleReader {
    public void read(ByteBuf var1, Particle var2) throws Exception;
}
