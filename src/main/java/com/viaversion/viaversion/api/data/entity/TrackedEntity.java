package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;

public interface TrackedEntity {
    public EntityType entityType();

    public StoredEntityData data();

    public boolean hasData();

    public boolean hasSentMetadata();

    public void sentMetadata(boolean var1);
}
