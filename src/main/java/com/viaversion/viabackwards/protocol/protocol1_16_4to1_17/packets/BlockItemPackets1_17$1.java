package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_17$1
extends PacketHandlers {
    BlockItemPackets1_17$1() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.handler(wrapper -> {
            short slot = wrapper.passthrough(Type.SHORT);
            byte button = wrapper.passthrough(Type.BYTE);
            wrapper.read(Type.SHORT);
            int mode = wrapper.passthrough(Type.VAR_INT);
            Item clicked = BlockItemPackets1_17.this.handleItemToServer(wrapper.read(Type.ITEM1_13_2));
            wrapper.write(Type.VAR_INT, 0);
            PlayerLastCursorItem state = wrapper.user().get(PlayerLastCursorItem.class);
            if (mode == 0 && button == 0 && clicked != null) {
                state.setLastCursorItem(clicked);
            } else if (mode == 0 && button == 1 && clicked != null) {
                if (state.isSet()) {
                    state.setLastCursorItem(clicked);
                } else {
                    state.setLastCursorItem(clicked, (clicked.amount() + 1) / 2);
                }
            } else if (mode != 5 || slot != -999 || button != 0 && button != 4) {
                state.setLastCursorItem(null);
            }
            Item carried = state.getLastCursorItem();
            if (carried == null) {
                wrapper.write(Type.ITEM1_13_2, clicked);
            } else {
                wrapper.write(Type.ITEM1_13_2, carried);
            }
        });
    }
}
