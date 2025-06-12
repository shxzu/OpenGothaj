package org.yaml.snakeyaml.representer;

import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentMap
implements Represent {
    protected SafeRepresenter$RepresentMap() {
    }

    @Override
    public Node representData(Object data) {
        return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map)data, DumperOptions.FlowStyle.AUTO);
    }
}
