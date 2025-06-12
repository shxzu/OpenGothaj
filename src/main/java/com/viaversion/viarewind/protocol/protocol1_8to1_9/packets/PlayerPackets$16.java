package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$16
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    PlayerPackets$16(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            packetWrapper.cancel();
            PacketWrapper delayedPacket = PacketWrapper.create(26, null, packetWrapper.user());
            delayedPacket.write(Type.VAR_INT, 0);
            protocol.animationsToSend.add(delayedPacket);
        });
        this.handler(packetWrapper -> {
            packetWrapper.user().get(BlockPlaceDestroyTracker.class).updateMining();
            packetWrapper.user().get(Cooldown.class).hit();
        });
    }
}
