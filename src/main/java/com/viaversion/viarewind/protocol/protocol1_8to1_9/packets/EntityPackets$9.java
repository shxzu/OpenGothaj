package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import java.util.List;

class EntityPackets$9
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    EntityPackets$9(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
        this.handler(wrapper -> {
            List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityTracker tracker = wrapper.user().get(EntityTracker.class);
            if (tracker.getClientEntityTypes().containsKey(entityId)) {
                this.val$protocol.getMetadataRewriter().transform(tracker, entityId, metadataList);
                if (metadataList.isEmpty()) {
                    wrapper.cancel();
                }
            } else {
                tracker.addMetadataToBuffer(entityId, metadataList);
                wrapper.cancel();
            }
        });
    }
}
