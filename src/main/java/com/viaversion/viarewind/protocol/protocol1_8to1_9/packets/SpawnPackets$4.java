package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import java.util.List;

class SpawnPackets$4
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    SpawnPackets$4(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.read(Type.UUID);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            short typeId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            int x = packetWrapper.get(Type.INT, 0);
            int y = packetWrapper.get(Type.INT, 1);
            int z = packetWrapper.get(Type.INT, 2);
            byte pitch = packetWrapper.get(Type.BYTE, 1);
            byte yaw = packetWrapper.get(Type.BYTE, 0);
            byte headYaw = packetWrapper.get(Type.BYTE, 2);
            if (typeId == 69) {
                packetWrapper.cancel();
                EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                ShulkerModel shulkerReplacement = new ShulkerModel(packetWrapper.user(), this.val$protocol, entityId);
                shulkerReplacement.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                shulkerReplacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                shulkerReplacement.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                tracker.addEntityReplacement(shulkerReplacement);
            } else if (typeId == -1 || typeId == 255) {
                packetWrapper.cancel();
            }
        });
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            short typeId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.getTypeFromId(typeId, false));
            tracker.sendMetadataBuffer(entityId);
        });
        this.handler(wrapper -> {
            List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = wrapper.user().get(EntityTracker.class);
            EntityModel replacement = tracker.getEntityReplacement(entityId);
            if (replacement != null) {
                replacement.updateMetadata(metadataList);
            } else if (tracker.getClientEntityTypes().containsKey(entityId)) {
                this.val$protocol.getMetadataRewriter().transform(tracker, entityId, metadataList);
            } else {
                wrapper.cancel();
            }
        });
    }
}
