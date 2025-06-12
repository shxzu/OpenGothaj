package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class BlockItemPackets1_11$4
extends PacketHandlers {
    BlockItemPackets1_11$4() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.VAR_INT);
        this.map(Type.ITEM1_8);
        this.handler(BlockItemPackets1_11.this.itemToServerHandler(Type.ITEM1_8));
        this.handler(wrapper -> {
            if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                Optional horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
                if (!horse.isPresent()) {
                    return;
                }
                ChestedHorseStorage storage = (ChestedHorseStorage)horse.get();
                short clickSlot = wrapper.get(Type.SHORT, 0);
                int correctSlot = BlockItemPackets1_11.this.getOldSlotId(storage, clickSlot);
                wrapper.set(Type.SHORT, 0, Integer.valueOf(correctSlot).shortValue());
            }
        });
    }
}
