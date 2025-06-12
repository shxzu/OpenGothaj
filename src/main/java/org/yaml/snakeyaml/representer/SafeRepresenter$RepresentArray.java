package org.yaml.snakeyaml.representer;

import java.util.Arrays;
import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentArray
implements Represent {
    protected SafeRepresenter$RepresentArray() {
    }

    @Override
    public Node representData(Object data) {
        Object[] array = (Object[])data;
        List<Object> list = Arrays.asList(array);
        return SafeRepresenter.this.representSequence(Tag.SEQ, list, DumperOptions.FlowStyle.AUTO);
    }
}
