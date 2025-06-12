package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class ParticleType$ParticleTypeFiller {
    private final ParticleMappings mappings;
    private final boolean useMappedNames;

    private ParticleType$ParticleTypeFiller(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
        this.mappings = protocol.getMappingData().getParticleMappings();
        this.useMappedNames = useMappedNames;
    }

    public ParticleType$ParticleTypeFiller reader(String identifier, ParticleType.ParticleReader reader) {
        ParticleType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.id(identifier), reader);
        return this;
    }

    public ParticleType$ParticleTypeFiller reader(int id, ParticleType.ParticleReader reader) {
        ParticleType.this.readers.put(id, reader);
        return this;
    }
}
