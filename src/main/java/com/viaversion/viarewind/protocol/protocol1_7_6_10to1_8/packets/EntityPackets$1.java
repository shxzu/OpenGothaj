package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class EntityPackets$1
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    EntityPackets$1(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map(Type.SHORT);
        this.map(Type.ITEM1_8, Types1_7_6_10.COMPRESSED_NBT_ITEM);
        this.handler(wrapper -> {
            Item item = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
            this.val$protocol.getItemRewriter().handleItemToClient(item);
            wrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
        });
        this.handler(wrapper -> {
            short slot = wrapper.get(Type.SHORT, 0);
            UUID uuid = wrapper.user().get(EntityTracker1_7_6_10.class).getPlayerUUID(wrapper.get(Type.INT, 0));
            if (uuid == null) {
                return;
            }
            Item item = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
            wrapper.user().get(PlayerSessionStorage.class).setPlayerEquipment(uuid, item, slot);
            GameProfileStorage storage = wrapper.user().get(GameProfileStorage.class);
            GameProfileStorage.GameProfile profile = storage.get(uuid);
            if (profile != null && profile.gamemode == 3) {
                wrapper.cancel();
            }
        });
    }
}
