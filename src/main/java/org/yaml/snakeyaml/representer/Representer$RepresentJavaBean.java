package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;

class Representer$RepresentJavaBean
implements Represent {
    protected Representer$RepresentJavaBean() {
    }

    @Override
    public Node representData(Object data) {
        return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
    }
}
