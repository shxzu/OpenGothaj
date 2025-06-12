package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import java.util.List;

class EntityPackets1_13_1$2
extends PacketHandlers {
    EntityPackets1_13_1$2() {
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
        this.map(Types1_13.METADATA_LIST);
        this.handler(EntityPackets1_13_1.this.getTrackerHandler());
        this.handler(wrapper -> {
            List<Metadata> metadata = wrapper.get(Types1_13.METADATA_LIST, 0);
            EntityPackets1_13_1.this.handleMetadata(wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
        });
    }
}
