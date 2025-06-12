// ERROR: Unable to apply inner class name fixup
package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class BlockItemPackets1_11.1
implements PacketHandler {
    BlockItemPackets1_11.1() {
    }

    @Override
    public void handle(PacketWrapper wrapper) throws Exception {
        if (this$0.isLlama(wrapper.user())) {
            Optional horse = this$0.getChestedHorse(wrapper.user());
            if (!horse.isPresent()) {
                return;
            }
            ChestedHorseStorage storage = (ChestedHorseStorage)horse.get();
            int currentSlot = wrapper.get(Type.SHORT, 0).shortValue();
            currentSlot = this$0.getNewSlotId(storage, currentSlot);
            wrapper.set(Type.SHORT, 0, Integer.valueOf(currentSlot).shortValue());
            wrapper.set(Type.ITEM1_8, 0, this$0.getNewItem(storage, currentSlot, wrapper.get(Type.ITEM1_8, 0)));
        }
    }
}
