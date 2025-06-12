package org.yaml.snakeyaml;

import java.util.Iterator;

class Yaml$1
implements Iterator<Object> {
    Yaml$1() {
    }

    @Override
    public boolean hasNext() {
        return Yaml.this.constructor.checkData();
    }

    @Override
    public Object next() {
        return Yaml.this.constructor.getData();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
