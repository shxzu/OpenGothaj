package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$13
extends PacketHandlers {
    PlayerPackets$13() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.POSITION1_8);
        this.handler(packetWrapper -> {
            int state = packetWrapper.get(Type.VAR_INT, 0);
            if (state == 0) {
                packetWrapper.user().get(BlockPlaceDestroyTracker.class).setMining(true);
            } else if (state == 2) {
                BlockPlaceDestroyTracker tracker = packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                tracker.setMining(false);
                tracker.setLastMining(System.currentTimeMillis() + 100L);
                packetWrapper.user().get(Cooldown.class).setLastHit(0L);
            } else if (state == 1) {
                BlockPlaceDestroyTracker tracker = packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                tracker.setMining(false);
                tracker.setLastMining(0L);
                packetWrapper.user().get(Cooldown.class).hit();
            }
        });
    }
}
