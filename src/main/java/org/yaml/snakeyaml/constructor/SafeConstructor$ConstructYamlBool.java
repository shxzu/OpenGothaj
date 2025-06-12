package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlBool
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        String val = SafeConstructor.this.constructScalar((ScalarNode)node);
        return BOOL_VALUES.get(val.toLowerCase());
    }
}
