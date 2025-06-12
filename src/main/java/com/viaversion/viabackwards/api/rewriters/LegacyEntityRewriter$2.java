package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class LegacyEntityRewriter$2
extends PacketHandlers {
    final EntityType val$playerType;

    LegacyEntityRewriter$2(EntityType entityType) {
        this.val$playerType = entityType;
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.INT);
        this.handler(wrapper -> {
            ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
            clientChunks.setEnvironment(wrapper.get(Type.INT, 1));
            LegacyEntityRewriter.this.addTrackedEntity(wrapper, wrapper.get(Type.INT, 0), this.val$playerType);
        });
    }
}
