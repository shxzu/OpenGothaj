package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;

@FunctionalInterface
public interface Mappings$MappingsSupplier<T extends Mappings> {
    public T supply(int[] var1, int var2);
}
