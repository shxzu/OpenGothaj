package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentNull
implements Represent {
    protected SafeRepresenter$RepresentNull() {
    }

    @Override
    public Node representData(Object data) {
        return SafeRepresenter.this.representScalar(Tag.NULL, "null");
    }
}
