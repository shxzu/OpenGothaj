package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import java.util.List;

class SpawnPackets$3
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    SpawnPackets$3(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            short typeId = wrapper.get(Type.UNSIGNED_BYTE, 0);
            if (typeId == 255 || typeId == -1) {
                wrapper.cancel();
            }
            EntityTypes1_10.EntityType type = EntityTypes1_10.getTypeFromId(typeId, false);
            int entityId = wrapper.get(Type.VAR_INT, 0);
            int x = wrapper.get(Type.INT, 0);
            int y = wrapper.get(Type.INT, 1);
            int z = wrapper.get(Type.INT, 2);
            byte pitch = wrapper.get(Type.BYTE, 1);
            byte yaw = wrapper.get(Type.BYTE, 0);
            byte headYaw = wrapper.get(Type.BYTE, 2);
            List<Metadata> metadataList = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
            if (type == EntityTypes1_10.EntityType.ARMOR_STAND) {
                VirtualHologramEntity hologram = new VirtualHologramEntity(wrapper.user(), this.val$protocol.getMetadataRewriter(), entityId);
                hologram.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                hologram.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                hologram.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                tracker.trackHologram(entityId, hologram);
                tracker.updateMetadata(entityId, metadataList);
                wrapper.cancel();
            } else {
                this.val$protocol.getMetadataRewriter().transform(type, metadataList);
                tracker.addEntity(entityId, type);
                if (tracker.isReplaced(type)) {
                    int newTypeId = tracker.replaceEntity(entityId, type);
                    wrapper.set(Type.UNSIGNED_BYTE, 0, (short)newTypeId);
                    tracker.updateMetadata(entityId, metadataList);
                }
            }
        });
    }
}
