package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_13$5
extends PacketHandlers {
    EntityPackets1_13$5() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.UUID);
        this.handler(EntityPackets1_13.this.getTrackerHandler(EntityTypes1_13.EntityType.PAINTING, Type.VAR_INT));
        this.handler(wrapper -> {
            int motive = wrapper.read(Type.VAR_INT);
            String title = PaintingMapping.getStringId(motive);
            wrapper.write(Type.STRING, title);
        });
    }
}
