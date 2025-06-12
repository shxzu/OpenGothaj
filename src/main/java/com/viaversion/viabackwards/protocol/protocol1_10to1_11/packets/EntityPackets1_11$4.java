package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import java.util.List;

class EntityPackets1_11$4
extends PacketHandlers {
    EntityPackets1_11$4() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Types1_9.METADATA_LIST);
        this.handler(EntityPackets1_11.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, EntityTypes1_11.EntityType.PLAYER));
        this.handler(wrapper -> {
            List<Metadata> metadata = wrapper.get(Types1_9.METADATA_LIST, 0);
            if (metadata.isEmpty()) {
                metadata.add(new Metadata(0, MetaType1_9.Byte, (byte)0));
            }
        });
    }
}
