package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$13
extends PacketHandlers {
    PlayerPackets$13() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            EntityTracker1_7_6_10 tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class);
            int entityId = packetWrapper.read(Type.VAR_INT);
            int spectating = tracker.spectatingPlayerId;
            if (spectating != entityId) {
                tracker.setSpectating(entityId);
            }
        });
    }
}
