package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19$2
extends PacketHandlers {
    BlockItemPackets1_19$2() {
    }

    @Override
    public void register() {
        this.read(Type.VAR_INT);
        this.handler(PacketWrapper::cancel);
    }
}
