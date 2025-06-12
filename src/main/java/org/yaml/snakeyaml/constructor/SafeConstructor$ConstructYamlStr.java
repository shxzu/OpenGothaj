package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlStr
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        return SafeConstructor.this.constructScalar((ScalarNode)node);
    }
}
