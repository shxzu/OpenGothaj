package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

public final class MetaType$MetaTypeImpl
implements MetaType {
    private final int typeId;
    private final Type<?> type;

    MetaType$MetaTypeImpl(int typeId, Type<?> type) {
        this.typeId = typeId;
        this.type = type;
    }

    @Override
    public int typeId() {
        return this.typeId;
    }

    @Override
    public Type<?> type() {
        return this.type;
    }

    public String toString() {
        return "MetaType{typeId=" + this.typeId + ", type=" + this.type + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MetaType$MetaTypeImpl metaType = (MetaType$MetaTypeImpl)o;
        if (this.typeId != metaType.typeId) {
            return false;
        }
        return this.type.equals(metaType.type);
    }

    public int hashCode() {
        int result = this.typeId;
        result = 31 * result + this.type.hashCode();
        return result;
    }
}
