package org.yaml.snakeyaml.representer;

import java.util.IdentityHashMap;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.Node;

class BaseRepresenter$1
extends IdentityHashMap<Object, Node> {
    private static final long serialVersionUID = -5576159264232131854L;

    BaseRepresenter$1() {
    }

    @Override
    public Node put(Object key, Node value) {
        return super.put(key, new AnchorNode(value));
    }
}
