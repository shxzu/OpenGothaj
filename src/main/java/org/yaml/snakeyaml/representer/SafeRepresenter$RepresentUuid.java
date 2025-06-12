package org.yaml.snakeyaml.representer;

import java.util.UUID;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentUuid
implements Represent {
    protected SafeRepresenter$RepresentUuid() {
    }

    @Override
    public Node representData(Object data) {
        return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), new Tag(UUID.class)), data.toString());
    }
}
