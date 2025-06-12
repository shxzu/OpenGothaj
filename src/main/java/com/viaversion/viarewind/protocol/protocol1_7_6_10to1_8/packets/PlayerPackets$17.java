package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.utils.math.AABB;
import com.viaversion.viarewind.utils.math.Ray3d;
import com.viaversion.viarewind.utils.math.RayTracing;
import com.viaversion.viarewind.utils.math.Vector3d;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$17
extends PacketHandlers {
    PlayerPackets$17() {
    }

    @Override
    public void register() {
        this.map((Type)Type.INT, Type.VAR_INT);
        this.map((Type)Type.BYTE, Type.VAR_INT);
        this.handler(wrapper -> {
            int mode = wrapper.get(Type.VAR_INT, 1);
            if (mode != 0) {
                return;
            }
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            PlayerSessionStorage position = wrapper.user().get(PlayerSessionStorage.class);
            if (tracker.getVirtualHologramMap().containsKey(entityId)) {
                AABB boundingBox = tracker.getVirtualHologramMap().get(entityId).getBoundingBox();
                Vector3d pos = new Vector3d(position.getPosX(), position.getPosY() + 1.8, position.getPosZ());
                double yaw = Math.toRadians(position.yaw);
                double pitch = Math.toRadians(position.pitch);
                Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                Ray3d ray = new Ray3d(pos, dir);
                Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0);
                if (intersection == null) {
                    return;
                }
                intersection.substract(boundingBox.getMin());
                mode = 2;
                wrapper.set(Type.VAR_INT, 1, mode);
                wrapper.write(Type.FLOAT, Float.valueOf((float)intersection.getX()));
                wrapper.write(Type.FLOAT, Float.valueOf((float)intersection.getY()));
                wrapper.write(Type.FLOAT, Float.valueOf((float)intersection.getZ()));
            }
        });
    }
}
