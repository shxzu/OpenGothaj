package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SpawnPackets$4
extends PacketHandlers {
    SpawnPackets$4() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            Position position = wrapper.read(Type.POSITION1_8);
            wrapper.write(Type.INT, position.x());
            wrapper.write(Type.INT, position.y());
            wrapper.write(Type.INT, position.z());
        });
        this.map((Type)Type.UNSIGNED_BYTE, Type.INT);
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.PAINTING);
        });
    }
}
