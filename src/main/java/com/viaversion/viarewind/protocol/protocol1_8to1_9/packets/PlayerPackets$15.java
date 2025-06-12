package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;

class PlayerPackets$15
extends PacketHandlers {
    PlayerPackets$15() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> packetWrapper.user().get(Cooldown.class).hit());
    }
}
