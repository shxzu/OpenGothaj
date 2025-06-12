package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.List;

class EntityPackets$12
extends PacketHandlers {
    EntityPackets$12() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            EntityTracker entityTracker = packetWrapper.user().get(EntityTracker.class);
            int vehicle = packetWrapper.read(Type.VAR_INT);
            int count = packetWrapper.read(Type.VAR_INT);
            ArrayList<Integer> passengers = new ArrayList<Integer>();
            for (int i = 0; i < count; ++i) {
                passengers.add(packetWrapper.read(Type.VAR_INT));
            }
            List<Integer> oldPassengers = entityTracker.getPassengers(vehicle);
            entityTracker.setPassengers(vehicle, passengers);
            if (!oldPassengers.isEmpty()) {
                for (Integer passenger : oldPassengers) {
                    PacketWrapper detach = PacketWrapper.create(ClientboundPackets1_8.ATTACH_ENTITY, null, packetWrapper.user());
                    detach.write(Type.INT, passenger);
                    detach.write(Type.INT, -1);
                    detach.write(Type.BOOLEAN, false);
                    PacketUtil.sendPacket(detach, Protocol1_8To1_9.class);
                }
            }
            for (int i = 0; i < count; ++i) {
                int v = i == 0 ? vehicle : passengers.get(i - 1);
                int p = passengers.get(i);
                PacketWrapper attach = PacketWrapper.create(ClientboundPackets1_8.ATTACH_ENTITY, null, packetWrapper.user());
                attach.write(Type.INT, p);
                attach.write(Type.INT, v);
                attach.write(Type.BOOLEAN, false);
                PacketUtil.sendPacket(attach, Protocol1_8To1_9.class);
            }
        });
    }
}
