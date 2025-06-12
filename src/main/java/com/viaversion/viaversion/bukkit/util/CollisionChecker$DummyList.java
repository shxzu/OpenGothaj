package com.viaversion.viaversion.bukkit.util;

import java.util.AbstractList;

class CollisionChecker$DummyList<T>
extends AbstractList<T> {
    private boolean any = false;

    private CollisionChecker$DummyList() {
    }

    @Override
    public T get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int idx, T el) {
        this.any = true;
    }

    @Override
    public int size() {
        return this.any ? 1 : 0;
    }
}
