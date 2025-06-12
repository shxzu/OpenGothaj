package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$5
extends PacketHandlers {
    PlayerPackets$5() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            if (ViaRewind.getConfig().isReplaceAdventureMode() && wrapper.get(Type.UNSIGNED_BYTE, 1) == 2) {
                wrapper.set(Type.UNSIGNED_BYTE, 1, (short)0);
            }
            wrapper.user().get(PlayerSessionStorage.class).gameMode = wrapper.get(Type.UNSIGNED_BYTE, 1).shortValue();
            ClientWorld world = wrapper.user().get(ClientWorld.class);
            Environment dimension = Environment.getEnvironmentById(wrapper.get(Type.INT, 0));
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            if (world.getEnvironment() != dimension) {
                world.setEnvironment(dimension.id());
                tracker.clear();
                tracker.getEntityMap().put(tracker.getPlayerId(), EntityTypes1_10.EntityType.ENTITY_HUMAN);
            }
        });
    }
}
