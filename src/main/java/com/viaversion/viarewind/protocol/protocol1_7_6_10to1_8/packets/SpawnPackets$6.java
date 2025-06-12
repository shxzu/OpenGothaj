package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SpawnPackets$6
extends PacketHandlers {
    SpawnPackets$6() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.LIGHTNING);
        });
    }
}
