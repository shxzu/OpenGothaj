package org.yaml.snakeyaml.constructor;

import java.util.Map;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;

public class SafeConstructor$ConstructYamlMap
implements Construct {
    @Override
    public Object construct(Node node) {
        MappingNode mnode = (MappingNode)node;
        if (node.isTwoStepsConstruction()) {
            return SafeConstructor.this.createDefaultMap(mnode.getValue().size());
        }
        return SafeConstructor.this.constructMapping(mnode);
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
        if (!node.isTwoStepsConstruction()) {
            throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
        }
        SafeConstructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
    }
}
