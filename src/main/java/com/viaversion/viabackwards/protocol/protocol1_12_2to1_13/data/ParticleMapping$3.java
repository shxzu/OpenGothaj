package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.List;

class ParticleMapping$3
implements ParticleMapping.ParticleHandler {
    ParticleMapping$3() {
    }

    @Override
    public int[] rewrite(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
        return this.rewrite(protocol, wrapper.read(Type.ITEM1_13));
    }

    @Override
    public int[] rewrite(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData<?>> data) {
        return this.rewrite(protocol, (Item)data.get(0).getValue());
    }

    private int[] rewrite(Protocol1_12_2To1_13 protocol, Item newItem) {
        Item item = protocol.getItemRewriter().handleItemToClient(newItem);
        return new int[]{item.identifier(), item.data()};
    }
}
