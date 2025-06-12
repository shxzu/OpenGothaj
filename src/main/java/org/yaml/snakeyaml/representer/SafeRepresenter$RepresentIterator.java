package org.yaml.snakeyaml.representer;

import java.util.Iterator;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.SafeRepresenter;

class SafeRepresenter$RepresentIterator
implements Represent {
    protected SafeRepresenter$RepresentIterator() {
    }

    @Override
    public Node representData(Object data) {
        Iterator iter = (Iterator)data;
        return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new SafeRepresenter.IteratorWrapper(iter), DumperOptions.FlowStyle.AUTO);
    }
}
