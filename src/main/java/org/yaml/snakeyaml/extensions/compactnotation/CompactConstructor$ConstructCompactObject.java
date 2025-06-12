package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.extensions.compactnotation.CompactData;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class CompactConstructor$ConstructCompactObject
extends Constructor.ConstructMapping {
    public CompactConstructor$ConstructCompactObject() {
        super(CompactConstructor.this);
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
        MappingNode mnode = (MappingNode)node;
        NodeTuple nodeTuple = mnode.getValue().iterator().next();
        Node valueNode = nodeTuple.getValueNode();
        if (valueNode instanceof MappingNode) {
            valueNode.setType(object.getClass());
            this.constructJavaBean2ndStep((MappingNode)valueNode, object);
        } else {
            CompactConstructor.this.applySequence(object, CompactConstructor.this.constructSequence((SequenceNode)valueNode));
        }
    }

    @Override
    public Object construct(Node node) {
        ScalarNode tmpNode;
        if (node instanceof MappingNode) {
            MappingNode mnode = (MappingNode)node;
            NodeTuple nodeTuple = mnode.getValue().iterator().next();
            node.setTwoStepsConstruction(true);
            tmpNode = (ScalarNode)nodeTuple.getKeyNode();
        } else {
            tmpNode = (ScalarNode)node;
        }
        CompactData data = CompactConstructor.this.getCompactData(tmpNode.getValue());
        if (data == null) {
            return CompactConstructor.this.constructScalar(tmpNode);
        }
        return CompactConstructor.this.constructCompactFormat(tmpNode, data);
    }
}
