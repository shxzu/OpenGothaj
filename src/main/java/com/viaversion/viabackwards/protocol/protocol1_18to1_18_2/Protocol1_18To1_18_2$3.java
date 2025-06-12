package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

class Protocol1_18To1_18_2$3
extends PacketHandlers {
    Protocol1_18To1_18_2$3() {
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
        this.handler(wrapper -> {
            CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            CompoundTag dimensionsHolder = (CompoundTag)registry.get("minecraft:dimension_type");
            ListTag dimensions = (ListTag)dimensionsHolder.get("value");
            for (Tag dimension : dimensions) {
                Protocol1_18To1_18_2.this.removeTagPrefix((CompoundTag)((CompoundTag)dimension).get("element"));
            }
            Protocol1_18To1_18_2.this.removeTagPrefix(wrapper.get(Type.NAMED_COMPOUND_TAG, 1));
        });
    }
}
