package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$2
extends PacketHandlers {
    PlayerPackets$2() {
    }

    @Override
    public void register() {
        this.map(Type.COMPONENT);
        this.handler(wrapper -> {
            byte position = wrapper.read(Type.BYTE);
            if (position == 2) {
                wrapper.cancel();
            }
        });
    }
}
