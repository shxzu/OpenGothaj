package org.yaml.snakeyaml;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.nodes.Node;

class Yaml$2
implements Iterator<Node> {
    final Composer val$composer;

    Yaml$2(Composer composer) {
        this.val$composer = composer;
    }

    @Override
    public boolean hasNext() {
        return this.val$composer.checkNode();
    }

    @Override
    public Node next() {
        Node node = this.val$composer.getNode();
        if (node != null) {
            return node;
        }
        throw new NoSuchElementException("No Node is available.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
