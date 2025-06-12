package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$12
extends PacketHandlers {
    EntityPackets$12() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            boolean leash = packetWrapper.get(Type.BOOLEAN, 0);
            if (!leash) {
                EntityTracker1_7_6_10 tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class);
                int passenger = packetWrapper.get(Type.INT, 0);
                int vehicle = packetWrapper.get(Type.INT, 1);
                tracker.setPassenger(vehicle, passenger);
            }
        });
    }
}
