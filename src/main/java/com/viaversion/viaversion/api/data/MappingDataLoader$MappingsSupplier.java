package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;

@FunctionalInterface
public interface MappingDataLoader$MappingsSupplier<T extends Mappings, V> {
    public T create(V var1, int var2);
}
