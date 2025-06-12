package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

class EntityPackets1_19$3
extends PacketHandlers {
    EntityPackets1_19$3() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.STRING_ARRAY);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.handler(wrapper -> {
            Object dimensionCompound;
            DimensionRegistryStorage dimensionRegistryStorage = wrapper.user().get(DimensionRegistryStorage.class);
            dimensionRegistryStorage.clear();
            String dimensionKey = wrapper.read(Type.STRING);
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            ListTag dimensions = (ListTag)((CompoundTag)registry.get("minecraft:dimension_type")).get("value");
            boolean found = false;
            for (Tag dimension : dimensions) {
                dimensionCompound = (CompoundTag)dimension;
                StringTag nameTag = (StringTag)((CompoundTag)dimensionCompound).get("name");
                CompoundTag dimensionData = (CompoundTag)((CompoundTag)dimensionCompound).get("element");
                dimensionRegistryStorage.addDimension(nameTag.getValue(), dimensionData.copy());
                if (found || !nameTag.getValue().equals(dimensionKey)) continue;
                wrapper.write(Type.NAMED_COMPOUND_TAG, dimensionData);
                found = true;
            }
            if (!found) {
                throw new IllegalStateException("Could not find dimension " + dimensionKey + " in dimension registry");
            }
            CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
            ListTag biomes = (ListTag)biomeRegistry.get("value");
            dimensionCompound = biomes.getValue().iterator();
            while (dimensionCompound.hasNext()) {
                Tag biome = (Tag)dimensionCompound.next();
                CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
                biomeCompound.put("category", new StringTag("none"));
            }
            EntityPackets1_19.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
            ListTag chatTypes = (ListTag)((CompoundTag)registry.remove("minecraft:chat_type")).get("value");
            for (Tag chatType : chatTypes) {
                CompoundTag chatTypeCompound = (CompoundTag)chatType;
                NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
                dimensionRegistryStorage.addChatType(idTag.asInt(), chatTypeCompound);
            }
        });
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.read(Type.OPTIONAL_GLOBAL_POSITION);
        this.handler(EntityPackets1_19.this.worldDataTrackerHandler(1));
        this.handler(EntityPackets1_19.this.playerTrackerHandler());
    }
}
