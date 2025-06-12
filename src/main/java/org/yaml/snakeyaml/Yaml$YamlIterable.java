package org.yaml.snakeyaml;

import java.util.Iterator;

class Yaml$YamlIterable
implements Iterable<Object> {
    private final Iterator<Object> iterator;

    public Yaml$YamlIterable(Iterator<Object> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<Object> iterator() {
        return this.iterator;
    }
}
