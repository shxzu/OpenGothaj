package org.yaml.snakeyaml.util;

import java.util.AbstractList;

class ArrayUtils$CompositeUnmodifiableArrayList<E>
extends AbstractList<E> {
    private final E[] array1;
    private final E[] array2;

    ArrayUtils$CompositeUnmodifiableArrayList(E[] array1, E[] array2) {
        this.array1 = array1;
        this.array2 = array2;
    }

    @Override
    public E get(int index) {
        E element;
        if (index < this.array1.length) {
            element = this.array1[index];
        } else if (index - this.array1.length < this.array2.length) {
            element = this.array2[index - this.array1.length];
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size());
        }
        return element;
    }

    @Override
    public int size() {
        return this.array1.length + this.array2.length;
    }
}
