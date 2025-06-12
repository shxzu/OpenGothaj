package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$18
extends PacketHandlers {
    PlayerPackets$18() {
    }

    @Override
    public void register() {
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.UNSIGNED_BYTE);
        this.handler(packetWrapper -> {
            int playerId;
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            int vehicle = tracker.getVehicle(playerId = tracker.getPlayerId());
            if (vehicle != -1 && tracker.getClientEntityTypes().get(vehicle) == EntityTypes1_10.EntityType.BOAT) {
                PacketWrapper steerBoat = PacketWrapper.create(17, null, packetWrapper.user());
                float left = packetWrapper.get(Type.FLOAT, 0).floatValue();
                float forward = packetWrapper.get(Type.FLOAT, 1).floatValue();
                steerBoat.write(Type.BOOLEAN, forward != 0.0f || left < 0.0f);
                steerBoat.write(Type.BOOLEAN, forward != 0.0f || left > 0.0f);
                PacketUtil.sendToServer(steerBoat, Protocol1_8To1_9.class);
            }
        });
    }
}
