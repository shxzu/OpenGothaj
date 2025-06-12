package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;

import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.Key;

class EntityPackets1_20$1
extends PacketHandlers {
    EntityPackets1_20$1() {
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
        this.map(Type.LONG);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.BOOLEAN);
        this.map(Type.OPTIONAL_GLOBAL_POSITION);
        this.read(Type.VAR_INT);
        this.handler(EntityPackets1_20.this.dimensionDataHandler());
        this.handler(EntityPackets1_20.this.biomeSizeTracker());
        this.handler(EntityPackets1_20.this.worldDataTrackerHandlerByKey());
        this.handler(wrapper -> {
            ListTag values;
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            if (registry.contains("minecraft:trim_pattern")) {
                values = (ListTag)((CompoundTag)registry.get("minecraft:trim_pattern")).get("value");
            } else {
                CompoundTag trimPatternRegistry = Protocol1_19_4To1_20.MAPPINGS.getTrimPatternRegistry().copy();
                registry.put("minecraft:trim_pattern", trimPatternRegistry);
                values = (ListTag)trimPatternRegistry.get("value");
            }
            for (Tag entry : values) {
                CompoundTag element = (CompoundTag)((CompoundTag)entry).get("element");
                StringTag templateItem = (StringTag)element.get("template_item");
                if (!EntityPackets1_20.this.newTrimPatterns.contains(Key.stripMinecraftNamespace(templateItem.getValue()))) continue;
                templateItem.setValue("minecraft:spire_armor_trim_smithing_template");
            }
        });
    }
}
