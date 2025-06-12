package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import java.util.List;

class EntityPackets1_14_1$2
extends PacketHandlers {
    EntityPackets1_14_1$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.map(Type.VAR_INT);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.DOUBLE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.map(Types1_14.METADATA_LIST);
        this.handler(wrapper -> {
            int entityId = wrapper.get(Type.VAR_INT, 0);
            int type = wrapper.get(Type.VAR_INT, 1);
            EntityPackets1_14_1.this.tracker(wrapper.user()).addEntity(entityId, EntityTypes1_14.getTypeFromId(type));
            List<Metadata> metadata = wrapper.get(Types1_14.METADATA_LIST, 0);
            EntityPackets1_14_1.this.handleMetadata(entityId, metadata, wrapper.user());
        });
    }
}
