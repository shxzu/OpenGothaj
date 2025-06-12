package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$5
extends PacketHandlers {
    EntityPackets$5() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT_ARRAY_PRIMITIVE, Types1_7_6_10.BYTE_INT_ARRAY);
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            for (int entityId : wrapper.get(Types1_7_6_10.BYTE_INT_ARRAY, 0)) {
                tracker.removeEntity(entityId);
            }
        });
    }
}
