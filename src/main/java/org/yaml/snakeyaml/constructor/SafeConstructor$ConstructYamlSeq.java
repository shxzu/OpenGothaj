package org.yaml.snakeyaml.constructor;

import java.util.List;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class SafeConstructor$ConstructYamlSeq
implements Construct {
    @Override
    public Object construct(Node node) {
        SequenceNode seqNode = (SequenceNode)node;
        if (node.isTwoStepsConstruction()) {
            return SafeConstructor.this.newList(seqNode);
        }
        return SafeConstructor.this.constructSequence(seqNode);
    }

    @Override
    public void construct2ndStep(Node node, Object data) {
        if (!node.isTwoStepsConstruction()) {
            throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
        }
        SafeConstructor.this.constructSequenceStep2((SequenceNode)node, (List)data);
    }
}
