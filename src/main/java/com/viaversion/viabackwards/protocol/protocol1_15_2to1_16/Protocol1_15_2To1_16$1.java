package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_15_2To1_16$1
extends PacketHandlers {
    Protocol1_15_2To1_16$1() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> Protocol1_15_2To1_16.this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT)));
        this.map(Type.BYTE);
        this.read(Type.UUID);
    }
}
