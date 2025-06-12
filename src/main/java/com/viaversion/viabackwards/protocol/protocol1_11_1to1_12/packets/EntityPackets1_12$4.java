package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

class EntityPackets1_12$4
extends PacketHandlers {
    EntityPackets1_12$4() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.INT);
        this.handler(EntityPackets1_12.this.getTrackerHandler(EntityTypes1_12.EntityType.PLAYER, Type.INT));
        this.handler(EntityPackets1_12.this.getDimensionHandler(1));
        this.handler(wrapper -> {
            ShoulderTracker tracker = wrapper.user().get(ShoulderTracker.class);
            tracker.setEntityId(wrapper.get(Type.INT, 0));
        });
        this.handler(packetWrapper -> {
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9_3.STATISTICS, packetWrapper.user());
            wrapper.write(Type.VAR_INT, 1);
            wrapper.write(Type.STRING, "achievement.openInventory");
            wrapper.write(Type.VAR_INT, 1);
            wrapper.scheduleSend(Protocol1_11_1To1_12.class);
        });
    }
}
