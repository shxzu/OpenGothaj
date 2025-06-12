package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;

class EntityPackets1_18$1
extends PacketHandlers {
    EntityPackets1_18$1() {
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
        this.map(Type.LONG);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.read(Type.VAR_INT);
        this.handler(EntityPackets1_18.this.worldDataTrackerHandler(1));
        this.handler(wrapper -> {
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
            ListTag biomes = (ListTag)biomeRegistry.get("value");
            Iterator iterator = biomes.getValue().iterator();
            while (iterator.hasNext()) {
                Tag biome = (Tag)iterator.next();
                CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
                StringTag category = (StringTag)biomeCompound.get("category");
                if (category.getValue().equals("mountain")) {
                    category.setValue("extreme_hills");
                }
                biomeCompound.put("depth", new FloatTag(0.125f));
                biomeCompound.put("scale", new FloatTag(0.05f));
            }
            EntityPackets1_18.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
        });
    }
}
