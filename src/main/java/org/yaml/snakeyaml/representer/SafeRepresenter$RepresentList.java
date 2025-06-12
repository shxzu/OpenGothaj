package org.yaml.snakeyaml.representer;

import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentList
implements Represent {
    protected SafeRepresenter$RepresentList() {
    }

    @Override
    public Node representData(Object data) {
        return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (List)data, DumperOptions.FlowStyle.AUTO);
    }
}
