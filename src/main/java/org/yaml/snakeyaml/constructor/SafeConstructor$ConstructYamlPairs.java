package org.yaml.snakeyaml.constructor;

import java.util.ArrayList;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class SafeConstructor$ConstructYamlPairs
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        if (!(node instanceof SequenceNode)) {
            throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + (Object)((Object)node.getNodeId()), node.getStartMark());
        }
        SequenceNode snode = (SequenceNode)node;
        ArrayList<Object[]> pairs = new ArrayList<Object[]>(snode.getValue().size());
        for (Node subnode : snode.getValue()) {
            if (!(subnode instanceof MappingNode)) {
                throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + (Object)((Object)subnode.getNodeId()), subnode.getStartMark());
            }
            MappingNode mnode = (MappingNode)subnode;
            if (mnode.getValue().size() != 1) {
                throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
            }
            Node keyNode = mnode.getValue().get(0).getKeyNode();
            Node valueNode = mnode.getValue().get(0).getValueNode();
            Object key = SafeConstructor.this.constructObject(keyNode);
            Object value = SafeConstructor.this.constructObject(valueNode);
            pairs.add(new Object[]{key, value});
        }
        return pairs;
    }
}
