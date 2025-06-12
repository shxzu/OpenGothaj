package com.viaversion.viabackwards.protocol.protocol1_9_1_2to1_9_3_4;

import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_9_1_2To1_9_3_4$3
extends PacketHandlers {
    Protocol1_9_1_2To1_9_3_4$3() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(wrapper -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            int dimensionId = wrapper.get(Type.INT, 0);
            clientWorld.setEnvironment(dimensionId);
        });
    }
}
