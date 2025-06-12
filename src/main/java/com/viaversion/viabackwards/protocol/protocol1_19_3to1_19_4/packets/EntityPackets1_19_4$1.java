package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

class EntityPackets1_19_4$1
extends PacketHandlers {
    EntityPackets1_19_4$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.STRING_ARRAY);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.map(Type.STRING);
        this.map(Type.STRING);
        this.handler(EntityPackets1_19_4.this.dimensionDataHandler());
        this.handler(EntityPackets1_19_4.this.biomeSizeTracker());
        this.handler(EntityPackets1_19_4.this.worldDataTrackerHandlerByKey());
        this.handler(wrapper -> {
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            registry.remove("minecraft:trim_pattern");
            registry.remove("minecraft:trim_material");
            registry.remove("minecraft:damage_type");
            CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
            ListTag biomes = (ListTag)biomeRegistry.get("value");
            for (Tag biomeTag : biomes) {
                CompoundTag biomeData;
                NumberTag hasPrecipitation = (NumberTag)(biomeData = (CompoundTag)((CompoundTag)biomeTag).get("element")).get("has_precipitation");
                biomeData.put("precipitation", new StringTag(hasPrecipitation.asByte() == 1 ? "rain" : "none"));
            }
        });
    }
}
