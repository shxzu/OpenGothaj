package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$16
extends PacketHandlers {
    EntityPackets$16() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.INT, 0);
            if (wrapper.user().get(EntityTracker1_7_6_10.class).getEntityReplacementMap().containsKey(entityId)) {
                wrapper.cancel();
                return;
            }
            int amount = wrapper.passthrough(Type.INT);
            for (int i = 0; i < amount; ++i) {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.DOUBLE);
                int modifierLength = wrapper.read(Type.VAR_INT);
                wrapper.write(Type.SHORT, (short)modifierLength);
                for (int j = 0; j < modifierLength; ++j) {
                    wrapper.passthrough(Type.UUID);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.BYTE);
                }
            }
        });
    }
}
