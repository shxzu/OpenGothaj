package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class InventoryPackets$2
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    InventoryPackets$2(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.handler(wrapper -> {
            short windowType = wrapper.user().get(InventoryTracker.class).get(wrapper.get(Type.UNSIGNED_BYTE, 0));
            Item[] items = wrapper.read(Type.ITEM1_8_SHORT_ARRAY);
            if (windowType == 4) {
                Item[] old = items;
                items = new Item[old.length - 1];
                items[0] = old[0];
                System.arraycopy(old, 2, items, 1, old.length - 3);
            }
            for (int i = 0; i < items.length; ++i) {
                items[i] = this.val$protocol.getItemRewriter().handleItemToClient(items[i]);
            }
            wrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, items);
        });
        this.handler(wrapper -> {
            GameProfileStorage.GameProfile profile;
            short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
            if (windowId != 0) {
                return;
            }
            UUID userId = wrapper.user().getProtocolInfo().getUuid();
            PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
            Item[] items = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, 0);
            for (int i = 5; i < 9; ++i) {
                playerSession.setPlayerEquipment(userId, items[i], 8 - i);
                if (!playerSession.isSpectator()) continue;
                items[i] = null;
            }
            if (playerSession.isSpectator() && (profile = wrapper.user().get(GameProfileStorage.class).get(userId)) != null) {
                items[5] = profile.getSkull();
            }
        });
    }
}
