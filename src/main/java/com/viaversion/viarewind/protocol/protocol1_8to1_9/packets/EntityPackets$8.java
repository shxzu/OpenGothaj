package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$8
extends PacketHandlers {
    EntityPackets$8() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            EntityModel replacement = tracker.getEntityReplacement(entityId);
            if (replacement != null) {
                packetWrapper.cancel();
                byte yaw = packetWrapper.get(Type.BYTE, 0);
                replacement.setHeadYaw((float)yaw * 360.0f / 256.0f);
            }
        });
    }
}
