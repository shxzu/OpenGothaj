package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$1
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    InventoryPackets$1(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.handler(wrapper -> {
            short windowType = wrapper.user().get(InventoryTracker.class).get(wrapper.get(Type.UNSIGNED_BYTE, 0));
            short slot = wrapper.get(Type.SHORT, 0);
            if (windowType == 4) {
                if (slot == 1) {
                    wrapper.cancel();
                } else if (slot >= 2) {
                    wrapper.set(Type.SHORT, 0, (short)(slot - 1));
                }
            }
        });
        this.map(Type.ITEM1_8, Types1_7_6_10.COMPRESSED_NBT_ITEM);
        this.handler(wrapper -> {
            Item item = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
            this.val$protocol.getItemRewriter().handleItemToClient(item);
            wrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
        });
        this.handler(wrapper -> {
            short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
            if (windowId != 0) {
                return;
            }
            short slot = wrapper.get(Type.SHORT, 0);
            if (slot < 5 || slot > 8) {
                return;
            }
            PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
            Item item = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
            playerSession.setPlayerEquipment(wrapper.user().getProtocolInfo().getUuid(), item, 8 - slot);
            if (playerSession.isSpectator()) {
                wrapper.cancel();
            }
        });
    }
}
