package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$1
extends PacketHandlers {
    PlayerPackets$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.read(Type.BOOLEAN);
        this.handler(wrapper -> {
            if (ViaRewind.getConfig().isReplaceAdventureMode() && wrapper.get(Type.UNSIGNED_BYTE, 0) == 2) {
                wrapper.set(Type.UNSIGNED_BYTE, 0, (short)0);
            }
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            tracker.setClientEntityId(wrapper.get(Type.INT, 0));
            tracker.addPlayer(wrapper.get(Type.INT, 0), wrapper.user().getProtocolInfo().getUuid());
            wrapper.user().get(PlayerSessionStorage.class).gameMode = wrapper.get(Type.UNSIGNED_BYTE, 0).shortValue();
            wrapper.user().get(ClientWorld.class).setEnvironment(wrapper.get(Type.BYTE, 0).byteValue());
            wrapper.user().put(new Scoreboard(wrapper.user()));
        });
    }
}
