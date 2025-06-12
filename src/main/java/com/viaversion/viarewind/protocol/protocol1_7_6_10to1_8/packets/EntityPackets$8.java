package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$8
extends PacketHandlers {
    EntityPackets$8() {
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.read(Type.BOOLEAN);
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            VirtualHologramEntity hologram = tracker.getVirtualHologramMap().get(wrapper.get(Type.INT, 0));
            if (hologram != null) {
                wrapper.cancel();
                byte yaw = wrapper.get(Type.BYTE, 0);
                byte pitch = wrapper.get(Type.BYTE, 1);
                hologram.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
            }
        });
    }
}
