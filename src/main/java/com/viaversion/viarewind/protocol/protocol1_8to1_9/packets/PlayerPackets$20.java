package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$20
extends PacketHandlers {
    PlayerPackets$20() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> packetWrapper.write(Type.BOOLEAN, false));
        this.map(Type.OPTIONAL_POSITION1_8);
    }
}
