package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$19
extends PacketHandlers {
    PlayerPackets$19() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.handler(packetWrapper -> {
            for (int i = 0; i < 4; ++i) {
                packetWrapper.write(Type.STRING, ChatUtil.jsonToLegacy(packetWrapper.read(Type.COMPONENT)));
            }
        });
    }
}
