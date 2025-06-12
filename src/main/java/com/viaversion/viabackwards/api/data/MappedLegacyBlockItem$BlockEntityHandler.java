package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

@FunctionalInterface
public interface MappedLegacyBlockItem$BlockEntityHandler {
    public CompoundTag handleOrNewCompoundTag(int var1, CompoundTag var2);
}
