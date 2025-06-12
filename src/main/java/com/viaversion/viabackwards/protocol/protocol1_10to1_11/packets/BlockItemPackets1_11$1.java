package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class BlockItemPackets1_11$1
extends PacketHandlers {
    BlockItemPackets1_11$1() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.ITEM1_8);
        this.handler(BlockItemPackets1_11.this.itemToClientHandler(Type.ITEM1_8));
        this.handler(new PacketHandler(){

            @Override
            public void handle(PacketWrapper wrapper) throws Exception {
                if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                    Optional horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
                    if (!horse.isPresent()) {
                        return;
                    }
                    ChestedHorseStorage storage = (ChestedHorseStorage)horse.get();
                    int currentSlot = wrapper.get(Type.SHORT, 0).shortValue();
                    currentSlot = BlockItemPackets1_11.this.getNewSlotId(storage, currentSlot);
                    wrapper.set(Type.SHORT, 0, Integer.valueOf(currentSlot).shortValue());
                    wrapper.set(Type.ITEM1_8, 0, BlockItemPackets1_11.this.getNewItem(storage, currentSlot, wrapper.get(Type.ITEM1_8, 0)));
                }
            }
        });
    }
}
