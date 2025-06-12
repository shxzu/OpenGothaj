package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$10
extends PacketHandlers {
    EntityPackets$10() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.read(Type.BOOLEAN);
        this.handler(wrapper -> {
            VirtualHologramEntity hologram;
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            int entityId = wrapper.get(Type.INT, 0);
            EntityTypes1_10.EntityType type = tracker.getEntityMap().get(entityId);
            if (type == EntityTypes1_10.EntityType.MINECART_ABSTRACT) {
                int y = wrapper.get(Type.INT, 2);
                wrapper.set(Type.INT, 2, y += 12);
            }
            if ((hologram = tracker.getVirtualHologramMap().get(entityId)) != null) {
                wrapper.cancel();
                int x = wrapper.get(Type.INT, 1);
                int y = wrapper.get(Type.INT, 2);
                int z = wrapper.get(Type.INT, 3);
                byte yaw = wrapper.get(Type.BYTE, 0);
                byte pitch = wrapper.get(Type.BYTE, 1);
                hologram.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                hologram.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
            }
        });
    }
}
