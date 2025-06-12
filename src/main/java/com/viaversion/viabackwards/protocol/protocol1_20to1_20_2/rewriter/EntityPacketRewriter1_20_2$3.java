package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.minecraft.GlobalPosition;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPacketRewriter1_20_2$3
extends PacketHandlers {
    EntityPacketRewriter1_20_2$3() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            wrapper.passthrough(Type.STRING);
            wrapper.passthrough(Type.STRING);
            wrapper.passthrough(Type.LONG);
            wrapper.write(Type.UNSIGNED_BYTE, wrapper.read(Type.BYTE).shortValue());
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.BOOLEAN);
            wrapper.passthrough(Type.BOOLEAN);
            GlobalPosition lastDeathPosition = wrapper.read(Type.OPTIONAL_GLOBAL_POSITION);
            int portalCooldown = wrapper.read(Type.VAR_INT);
            wrapper.passthrough(Type.BYTE);
            wrapper.write(Type.OPTIONAL_GLOBAL_POSITION, lastDeathPosition);
            wrapper.write(Type.VAR_INT, portalCooldown);
        });
        this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
    }
}
