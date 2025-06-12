package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$13
extends PacketHandlers {
    EntityPackets$13() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            EntityTypes1_10.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
            if (type == EntityTypes1_10.EntityType.BOAT) {
                byte yaw = packetWrapper.get(Type.BYTE, 1);
                yaw = (byte)(yaw - 64);
                packetWrapper.set(Type.BYTE, 0, yaw);
                int y = packetWrapper.get(Type.INT, 1);
                packetWrapper.set(Type.INT, 1, y += 10);
            }
        });
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            packetWrapper.user().get(EntityTracker.class).resetEntityOffset(entityId);
        });
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            EntityModel replacement = tracker.getEntityReplacement(entityId);
            if (replacement != null) {
                packetWrapper.cancel();
                int x = packetWrapper.get(Type.INT, 0);
                int y = packetWrapper.get(Type.INT, 1);
                int z = packetWrapper.get(Type.INT, 2);
                byte yaw = packetWrapper.get(Type.BYTE, 0);
                byte pitch = packetWrapper.get(Type.BYTE, 1);
                replacement.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
            }
        });
    }
}
