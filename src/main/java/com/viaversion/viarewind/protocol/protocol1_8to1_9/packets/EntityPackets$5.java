package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$5
extends PacketHandlers {
    EntityPackets$5() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            int vehicle = tracker.getVehicle(tracker.getPlayerId());
            if (vehicle == -1) {
                packetWrapper.cancel();
            }
            packetWrapper.write(Type.VAR_INT, vehicle);
        });
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.FLOAT, Protocol1_8To1_9.DEGREES_TO_ANGLE);
        this.map(Type.FLOAT, Protocol1_8To1_9.DEGREES_TO_ANGLE);
        this.handler(packetWrapper -> {
            if (packetWrapper.isCancelled()) {
                return;
            }
            PlayerPosition position = packetWrapper.user().get(PlayerPosition.class);
            double x = (double)packetWrapper.get(Type.INT, 0).intValue() / 32.0;
            double y = (double)packetWrapper.get(Type.INT, 1).intValue() / 32.0;
            double z = (double)packetWrapper.get(Type.INT, 2).intValue() / 32.0;
            position.setPos(x, y, z);
        });
        this.create(Type.BOOLEAN, true);
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
    }
}
