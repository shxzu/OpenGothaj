package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_19_4$1
extends PacketHandlers {
    BlockItemPackets1_19_4$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.COMPONENT);
        this.handler(wrapper -> {
            int windowType = wrapper.get(Type.VAR_INT, 1);
            if (windowType == 21) {
                wrapper.cancel();
            } else if (windowType > 21) {
                wrapper.set(Type.VAR_INT, 1, windowType - 1);
            }
            ((Protocol1_19_3To1_19_4)BlockItemPackets1_19_4.this.protocol).getTranslatableRewriter().processText(wrapper.get(Type.COMPONENT, 0));
        });
    }
}
