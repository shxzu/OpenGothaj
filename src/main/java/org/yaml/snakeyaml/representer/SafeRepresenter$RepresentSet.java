package org.yaml.snakeyaml.representer;

import java.util.LinkedHashMap;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentSet
implements Represent {
    protected SafeRepresenter$RepresentSet() {
    }

    @Override
    public Node representData(Object data) {
        LinkedHashMap value = new LinkedHashMap();
        Set set = (Set)data;
        for (Object key : set) {
            value.put(key, null);
        }
        return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, DumperOptions.FlowStyle.AUTO);
    }
}
