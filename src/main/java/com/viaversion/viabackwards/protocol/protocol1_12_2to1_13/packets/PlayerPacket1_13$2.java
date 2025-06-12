package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPacket1_13$2
extends PacketHandlers {
    PlayerPacket1_13$2() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            ParticleMapping.ParticleData old = ParticleMapping.getMapping(wrapper.get(Type.INT, 0));
            wrapper.set(Type.INT, 0, old.getHistoryId());
            int[] data = old.rewriteData((Protocol1_12_2To1_13)PlayerPacket1_13.this.protocol, wrapper);
            if (data != null) {
                if (old.getHandler().isBlockHandler() && data[0] == 0) {
                    wrapper.cancel();
                    return;
                }
                for (int i : data) {
                    wrapper.write(Type.VAR_INT, i);
                }
            }
        });
    }
}
