package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SoundPackets1_12$2
extends PacketHandlers {
    SoundPackets1_12$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(wrapper -> {
            int oldId = wrapper.get(Type.VAR_INT, 0);
            int newId = SoundPackets1_12.this.handleSounds(oldId);
            if (newId == -1) {
                wrapper.cancel();
                return;
            }
            if (SoundPackets1_12.this.hasPitch(oldId)) {
                wrapper.set(Type.FLOAT, 1, Float.valueOf(SoundPackets1_12.this.handlePitch(oldId)));
            }
            wrapper.set(Type.VAR_INT, 0, newId);
        });
    }
}
