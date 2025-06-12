package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$9
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    PlayerPackets$9(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            int playerId;
            PacketWrapper animation = null;
            while ((animation = protocol.animationsToSend.poll()) != null) {
                PacketUtil.sendToServer(animation, Protocol1_8To1_9.class, true, true);
            }
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            if (tracker.isInsideVehicle(playerId = tracker.getPlayerId())) {
                packetWrapper.cancel();
            }
        });
    }
}
