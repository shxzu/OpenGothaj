package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_13$2
extends PacketHandlers {
    BlockItemPackets1_13$2() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.handler(wrapper -> {
            BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 5) {
                wrapper.cancel();
            }
            wrapper.set(Type.NAMED_COMPOUND_TAG, 0, provider.transform(wrapper.user(), wrapper.get(Type.POSITION1_8, 0), wrapper.get(Type.NAMED_COMPOUND_TAG, 0)));
        });
    }
}
