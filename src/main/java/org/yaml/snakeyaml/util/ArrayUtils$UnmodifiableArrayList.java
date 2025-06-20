package org.yaml.snakeyaml.util;

import java.util.AbstractList;

class ArrayUtils$UnmodifiableArrayList<E>
extends AbstractList<E> {
    private final E[] array;

    ArrayUtils$UnmodifiableArrayList(E[] array) {
        this.array = array;
    }

    @Override
    public E get(int index) {
        if (index >= this.array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size());
        }
        return this.array[index];
    }

    @Override
    public int size() {
        return this.array.length;
    }
}
