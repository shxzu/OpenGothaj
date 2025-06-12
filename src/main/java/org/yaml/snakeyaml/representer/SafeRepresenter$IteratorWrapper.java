package org.yaml.snakeyaml.representer;

import java.util.Iterator;

class SafeRepresenter$IteratorWrapper
implements Iterable<Object> {
    private final Iterator<Object> iter;

    public SafeRepresenter$IteratorWrapper(Iterator<Object> iter) {
        this.iter = iter;
    }

    @Override
    public Iterator<Object> iterator() {
        return this.iter;
    }
}
