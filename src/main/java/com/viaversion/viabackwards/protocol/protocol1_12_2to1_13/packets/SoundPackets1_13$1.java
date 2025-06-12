package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SoundPackets1_13$1
extends PacketHandlers {
    SoundPackets1_13$1() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int newSound = wrapper.get(Type.VAR_INT, 0);
            int oldSound = ((Protocol1_12_2To1_13)SoundPackets1_13.this.protocol).getMappingData().getSoundMappings().getNewId(newSound);
            if (oldSound == -1) {
                wrapper.cancel();
            } else {
                wrapper.set(Type.VAR_INT, 0, oldSound);
            }
        });
    }
}
