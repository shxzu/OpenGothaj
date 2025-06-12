package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import java.util.List;

class EntityPackets$13
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    EntityPackets$13(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map((Type)Type.VAR_INT, Type.INT);
        this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.INT, 0);
            List<Metadata> metadataList = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            if (tracker.getEntityReplacementMap().containsKey(entityId)) {
                tracker.updateMetadata(entityId, metadataList);
                wrapper.cancel();
                return;
            }
            if (tracker.getEntityMap().containsKey(entityId)) {
                this.val$protocol.getMetadataRewriter().transform(tracker.getEntityMap().get(entityId), metadataList);
                if (metadataList.isEmpty()) {
                    wrapper.cancel();
                }
            } else {
                wrapper.cancel();
            }
        });
    }
}
