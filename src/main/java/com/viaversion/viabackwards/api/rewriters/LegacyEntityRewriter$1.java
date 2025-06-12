package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class LegacyEntityRewriter$1
extends PacketHandlers {
    LegacyEntityRewriter$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(wrapper -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            clientWorld.setEnvironment(wrapper.get(Type.INT, 0));
        });
    }
}
