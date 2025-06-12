package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class PlayerPackets$8
extends PacketHandlers {
    PlayerPackets$8() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.FLOAT);
        this.handler(wrapper -> {
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) != 3) {
                return;
            }
            int gameMode = wrapper.get(Type.FLOAT, 0).intValue();
            PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
            if (gameMode == 3 || playerSession.gameMode == 3) {
                UUID myId = wrapper.user().getProtocolInfo().getUuid();
                Item[] equipment = new Item[4];
                if (gameMode == 3) {
                    GameProfileStorage.GameProfile profile = wrapper.user().get(GameProfileStorage.class).get(myId);
                    equipment[3] = profile == null ? null : profile.getSkull();
                } else {
                    for (int i = 0; i < equipment.length; ++i) {
                        equipment[i] = playerSession.getPlayerEquipment(myId, i);
                    }
                }
                for (int i = 0; i < equipment.length; ++i) {
                    PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_SLOT, wrapper.user());
                    setSlot.write(Type.BYTE, (byte)0);
                    setSlot.write(Type.SHORT, (short)(8 - i));
                    setSlot.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[i]);
                    PacketUtil.sendPacket(setSlot, Protocol1_7_6_10To1_8.class);
                }
            }
            if (gameMode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                gameMode = 0;
                wrapper.set(Type.FLOAT, 0, Float.valueOf(0.0f));
            }
            wrapper.user().get(PlayerSessionStorage.class).gameMode = gameMode;
        });
    }
}
