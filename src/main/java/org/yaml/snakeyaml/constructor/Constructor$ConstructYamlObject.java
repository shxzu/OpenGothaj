package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.nodes.Node;

class Constructor$ConstructYamlObject
implements Construct {
    protected Constructor$ConstructYamlObject() {
    }

    private Construct getConstructor(Node node) {
        Class<?> cl = Constructor.this.getClassForNode(node);
        node.setType(cl);
        Construct constructor = (Construct)Constructor.this.yamlClassConstructors.get((Object)node.getNodeId());
        return constructor;
    }

    @Override
    public Object construct(Node node) {
        try {
            return this.getConstructor(node).construct(node);
        }
        catch (ConstructorException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
        }
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
        try {
            this.getConstructor(node).construct2ndStep(node, object);
        }
        catch (Exception e) {
            throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
        }
    }
}
