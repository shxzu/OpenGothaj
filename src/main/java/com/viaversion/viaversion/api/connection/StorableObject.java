package com.viaversion.viaversion.api.connection;

public interface StorableObject {
    default public boolean clearOnServerSwitch() {
        return true;
    }

    default public void onRemove() {
    }
}
