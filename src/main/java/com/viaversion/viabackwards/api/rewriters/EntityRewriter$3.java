package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityRewriter$3
extends PacketHandlers {
    EntityRewriter$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.handler(wrapper -> EntityRewriter.this.trackAndMapEntity(wrapper));
    }
}
