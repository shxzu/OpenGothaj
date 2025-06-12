package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

class EntityPackets1_19$4
extends PacketHandlers {
    EntityPackets1_19$4() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            String dimensionKey = wrapper.read(Type.STRING);
            CompoundTag dimension = wrapper.user().get(DimensionRegistryStorage.class).dimension(dimensionKey);
            if (dimension == null) {
                throw new IllegalArgumentException("Could not find dimension " + dimensionKey + " in dimension registry");
            }
            wrapper.write(Type.NAMED_COMPOUND_TAG, dimension);
        });
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.BYTE);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.read(Type.OPTIONAL_GLOBAL_POSITION);
        this.handler(EntityPackets1_19.this.worldDataTrackerHandler(0));
    }
}
