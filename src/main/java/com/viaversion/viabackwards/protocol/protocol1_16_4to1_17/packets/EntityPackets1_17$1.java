package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

class EntityPackets1_17$1
extends PacketHandlers {
    EntityPackets1_17$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.STRING_ARRAY);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.map(Type.STRING);
        this.handler(wrapper -> {
            byte previousGamemode = wrapper.get(Type.BYTE, 1);
            if (previousGamemode == -1) {
                wrapper.set(Type.BYTE, 1, (byte)0);
            }
        });
        this.handler(EntityPackets1_17.this.getTrackerHandler(EntityTypes1_17.PLAYER, Type.INT));
        this.handler(EntityPackets1_17.this.worldDataTrackerHandler(1));
        this.handler(wrapper -> {
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
            ListTag biomes = (ListTag)biomeRegistry.get("value");
            for (Tag biome : biomes) {
                CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
                StringTag category = (StringTag)biomeCompound.get("category");
                if (!category.getValue().equalsIgnoreCase("underground")) continue;
                category.setValue("none");
            }
            CompoundTag dimensionRegistry = (CompoundTag)registry.get("minecraft:dimension_type");
            ListTag dimensions = (ListTag)dimensionRegistry.get("value");
            for (Tag dimension : dimensions) {
                CompoundTag dimensionCompound = (CompoundTag)((CompoundTag)dimension).get("element");
                EntityPackets1_17.this.reduceExtendedHeight(dimensionCompound, false);
            }
            EntityPackets1_17.this.reduceExtendedHeight(wrapper.get(Type.NAMED_COMPOUND_TAG, 1), true);
        });
    }
}
