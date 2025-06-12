package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.nodes.Node;

public final class SafeConstructor$ConstructUndefined
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        throw new ConstructorException(null, null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark());
    }
}
