package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$15
extends PacketHandlers {
    EntityPackets$15() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.handler(packetWrapper -> {
            byte id = packetWrapper.get(Type.BYTE, 0);
            if (id > 23) {
                packetWrapper.cancel();
            }
            if (id == 25) {
                if (packetWrapper.get(Type.VAR_INT, 0).intValue() != packetWrapper.user().get(EntityTracker.class).getPlayerId()) {
                    return;
                }
                Levitation levitation = packetWrapper.user().get(Levitation.class);
                levitation.setActive(true);
                levitation.setAmplifier(packetWrapper.get(Type.BYTE, 1).byteValue());
            }
        });
    }
}
