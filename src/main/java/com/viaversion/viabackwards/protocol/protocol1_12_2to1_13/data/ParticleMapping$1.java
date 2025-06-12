package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.List;

class ParticleMapping$1
implements ParticleMapping.ParticleHandler {
    ParticleMapping$1() {
    }

    @Override
    public int[] rewrite(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
        return this.rewrite(wrapper.read(Type.VAR_INT));
    }

    @Override
    public int[] rewrite(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData<?>> data) {
        return this.rewrite((Integer)data.get(0).getValue());
    }

    private int[] rewrite(int newType) {
        int blockType = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(newType);
        int type = blockType >> 4;
        int meta = blockType & 0xF;
        return new int[]{type + (meta << 12)};
    }

    @Override
    public boolean isBlockHandler() {
        return true;
    }
}
