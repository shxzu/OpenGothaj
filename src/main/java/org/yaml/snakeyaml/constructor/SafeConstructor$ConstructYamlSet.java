package org.yaml.snakeyaml.constructor;

import java.util.Set;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;

public class SafeConstructor$ConstructYamlSet
implements Construct {
    @Override
    public Object construct(Node node) {
        if (node.isTwoStepsConstruction()) {
            return SafeConstructor.this.constructedObjects.containsKey(node) ? SafeConstructor.this.constructedObjects.get(node) : SafeConstructor.this.createDefaultSet(((MappingNode)node).getValue().size());
        }
        return SafeConstructor.this.constructSet((MappingNode)node);
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
        if (!node.isTwoStepsConstruction()) {
            throw new YAMLException("Unexpected recursive set structure. Node: " + node);
        }
        SafeConstructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
    }
}
