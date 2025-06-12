package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$6
extends PacketHandlers {
    EntityPackets$6() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
        this.handler(packetWrapper -> {
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            for (int entityId : packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                tracker.removeEntity(entityId);
            }
        });
    }
}
