package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class Protocol1_13To1_13_1$2
extends PacketHandlers {
    Protocol1_13To1_13_1$2() {
    }

    @Override
    public void register() {
        this.map(Type.ITEM1_13);
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            Protocol1_13To1_13_1.this.itemRewriter.handleItemToServer(wrapper.get(Type.ITEM1_13, 0));
            wrapper.write(Type.VAR_INT, 0);
        });
    }
}
