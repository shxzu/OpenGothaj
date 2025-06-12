package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentEnum
implements Represent {
    protected SafeRepresenter$RepresentEnum() {
    }

    @Override
    public Node representData(Object data) {
        Tag tag = new Tag(data.getClass());
        return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), ((Enum)data).name());
    }
}
