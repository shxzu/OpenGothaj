package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.BlockItemPackets1_11;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Arrays;
import java.util.Optional;

class BlockItemPackets1_11$2
extends PacketHandlers {
    BlockItemPackets1_11$2() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.ITEM1_8_SHORT_ARRAY);
        this.handler(wrapper -> {
            Item[] stacks = wrapper.get(Type.ITEM1_8_SHORT_ARRAY, 0);
            for (int i = 0; i < stacks.length; ++i) {
                stacks[i] = BlockItemPackets1_11.this.handleItemToClient(stacks[i]);
            }
            if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
                Optional horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
                if (!horse.isPresent()) {
                    return;
                }
                ChestedHorseStorage storage = (ChestedHorseStorage)horse.get();
                stacks = Arrays.copyOf(stacks, !storage.isChested() ? 38 : 53);
                for (int i = stacks.length - 1; i >= 0; --i) {
                    stacks[((BlockItemPackets1_11)BlockItemPackets1_11.this).getNewSlotId((ChestedHorseStorage)storage, (int)i)] = stacks[i];
                    stacks[i] = BlockItemPackets1_11.this.getNewItem(storage, i, stacks[i]);
                }
                wrapper.set(Type.ITEM1_8_SHORT_ARRAY, 0, stacks);
            }
        });
    }
}
