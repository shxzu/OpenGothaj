package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public final class Particle$ParticleData<T> {
    private final Type<T> type;
    private T value;

    public Particle$ParticleData(Type<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    public Type<T> getType() {
        return this.type;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void write(ByteBuf buf) throws Exception {
        this.type.write(buf, this.value);
    }

    public void write(PacketWrapper wrapper) {
        wrapper.write(this.type, this.value);
    }

    public String toString() {
        return "ParticleData{type=" + this.type + ", value=" + this.value + '}';
    }
}
