package org.yaml.snakeyaml;

import java.util.Iterator;
import org.yaml.snakeyaml.nodes.Node;

class Yaml$NodeIterable
implements Iterable<Node> {
    private final Iterator<Node> iterator;

    public Yaml$NodeIterable(Iterator<Node> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<Node> iterator() {
        return this.iterator;
    }
}
