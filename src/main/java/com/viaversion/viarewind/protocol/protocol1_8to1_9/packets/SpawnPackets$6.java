package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import java.util.List;

class SpawnPackets$6
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    SpawnPackets$6(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.handler(packetWrapper -> packetWrapper.write(Type.SHORT, (short)0));
        this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
            this.val$protocol.getMetadataRewriter().transform(wrapper.user().get(EntityTracker.class), entityId, metadataList, EntityTypes1_10.EntityType.PLAYER);
        });
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.PLAYER);
            tracker.sendMetadataBuffer(entityId);
        });
    }
}
