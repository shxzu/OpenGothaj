package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import java.util.ArrayList;

class EntityPackets1_15$5
extends PacketHandlers {
    EntityPackets1_15$5() {
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
        this.handler(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList()));
        this.handler(EntityPackets1_15.this.getTrackerHandler(EntityTypes1_15.PLAYER, Type.VAR_INT));
    }
}
