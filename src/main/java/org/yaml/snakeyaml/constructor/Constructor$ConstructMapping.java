package org.yaml.snakeyaml.constructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

class Constructor$ConstructMapping
implements Construct {
    protected Constructor$ConstructMapping() {
    }

    @Override
    public Object construct(Node node) {
        MappingNode mnode = (MappingNode)node;
        if (Map.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
                return Constructor.this.newMap(mnode);
            }
            return Constructor.this.constructMapping(mnode);
        }
        if (Collection.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
                return Constructor.this.newSet(mnode);
            }
            return Constructor.this.constructSet(mnode);
        }
        Object obj = Constructor.this.newInstance(mnode);
        if (obj != BaseConstructor.NOT_INSTANTIATED_OBJECT) {
            if (node.isTwoStepsConstruction()) {
                return obj;
            }
            return this.constructJavaBean2ndStep(mnode, obj);
        }
        throw new ConstructorException(null, null, "Can't create an instance for " + mnode.getTag(), node.getStartMark());
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
        if (Map.class.isAssignableFrom(node.getType())) {
            Constructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
        } else if (Set.class.isAssignableFrom(node.getType())) {
            Constructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
        } else {
            this.constructJavaBean2ndStep((MappingNode)node, object);
        }
    }

    protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
        Constructor.this.flattenMapping(node, true);
        Class<? extends Object> beanType = node.getType();
        List<NodeTuple> nodeValue = node.getValue();
        for (NodeTuple tuple : nodeValue) {
            Node valueNode = tuple.getValueNode();
            String key = (String)Constructor.this.constructObject(tuple.getKeyNode());
            try {
                Object value;
                Class<?>[] arguments;
                boolean typeDetected;
                Property property;
                TypeDescription memberDescription = (TypeDescription)Constructor.this.typeDefinitions.get(beanType);
                Property property2 = property = memberDescription == null ? this.getProperty(beanType, key) : memberDescription.getProperty(key);
                if (!property.isWritable()) {
                    throw new YAMLException("No writable property '" + key + "' on class: " + beanType.getName());
                }
                valueNode.setType(property.getType());
                boolean bl = typeDetected = memberDescription != null && memberDescription.setupPropertyType(key, valueNode);
                if (!typeDetected && valueNode.getNodeId() != NodeId.scalar && (arguments = property.getActualTypeArguments()) != null && arguments.length > 0) {
                    Class<?> t;
                    if (valueNode.getNodeId() == NodeId.sequence) {
                        t = arguments[0];
                        SequenceNode snode = (SequenceNode)valueNode;
                        snode.setListType(t);
                    } else if (Map.class.isAssignableFrom(valueNode.getType())) {
                        Class<?> keyType = arguments[0];
                        Class<?> valueType = arguments[1];
                        MappingNode mnode = (MappingNode)valueNode;
                        mnode.setTypes(keyType, valueType);
                        mnode.setUseClassConstructor(true);
                    } else if (Collection.class.isAssignableFrom(valueNode.getType())) {
                        t = arguments[0];
                        MappingNode mnode = (MappingNode)valueNode;
                        mnode.setOnlyKeyType(t);
                        mnode.setUseClassConstructor(true);
                    }
                }
                Object object2 = value = memberDescription != null ? this.newInstance(memberDescription, key, valueNode) : Constructor.this.constructObject(valueNode);
                if ((property.getType() == Float.TYPE || property.getType() == Float.class) && value instanceof Double) {
                    value = Float.valueOf(((Double)value).floatValue());
                }
                if (property.getType() == String.class && Tag.BINARY.equals(valueNode.getTag()) && value instanceof byte[]) {
                    value = new String((byte[])value);
                }
                if (memberDescription != null && memberDescription.setProperty(object, key, value)) continue;
                property.set(object, value);
            }
            catch (DuplicateKeyException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ConstructorException("Cannot create property=" + key + " for JavaBean=" + object, node.getStartMark(), e.getMessage(), valueNode.getStartMark(), e);
            }
        }
        return object;
    }

    private Object newInstance(TypeDescription memberDescription, String propertyName, Node node) {
        Object newInstance = memberDescription.newInstance(propertyName, node);
        if (newInstance != null) {
            Constructor.this.constructedObjects.put(node, newInstance);
            return Constructor.this.constructObjectNoCheck(node);
        }
        return Constructor.this.constructObject(node);
    }

    protected Property getProperty(Class<? extends Object> type, String name) {
        return Constructor.this.getPropertyUtils().getProperty(type, name);
    }
}
