package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityRewriter$2
extends PacketHandlers {
    final EntityType val$fallingBlockType;

    EntityRewriter$2(EntityType entityType) {
        this.val$fallingBlockType = entityType;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.handler(EntityRewriter.this.getSpawnTrackerWithDataHandler1_19(this.val$fallingBlockType));
    }
}
