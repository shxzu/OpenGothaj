package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentBoolean
implements Represent {
    protected SafeRepresenter$RepresentBoolean() {
    }

    @Override
    public Node representData(Object data) {
        String value = Boolean.TRUE.equals(data) ? "true" : "false";
        return SafeRepresenter.this.representScalar(Tag.BOOL, value);
    }
}
