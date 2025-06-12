package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_18To1_18_2$1
extends PacketHandlers {
    final PacketHandler val$entityEffectIdHandler;

    Protocol1_18To1_18_2$1(PacketHandler packetHandler) {
        this.val$entityEffectIdHandler = packetHandler;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(this.val$entityEffectIdHandler);
    }
}
