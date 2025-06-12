package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;

class EntityPackets1_13_2$2
extends PacketHandlers {
    EntityPackets1_13_2$2() {
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
        this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
        this.handler(wrapper -> {
            for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0)) {
                metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
            }
        });
    }
}
