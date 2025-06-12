package org.yaml.snakeyaml.constructor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

class Constructor$ConstructSequence
implements Construct {
    protected Constructor$ConstructSequence() {
    }

    @Override
    public Object construct(Node node) {
        SequenceNode snode = (SequenceNode)node;
        if (Set.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Set cannot be recursive.");
            }
            return Constructor.this.constructSet(snode);
        }
        if (Collection.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
                return Constructor.this.newList(snode);
            }
            return Constructor.this.constructSequence(snode);
        }
        if (node.getType().isArray()) {
            if (node.isTwoStepsConstruction()) {
                return Constructor.this.createArray(node.getType(), snode.getValue().size());
            }
            return Constructor.this.constructArray(snode);
        }
        ArrayList possibleConstructors = new ArrayList(snode.getValue().size());
        for (Constructor<?> constructor : node.getType().getDeclaredConstructors()) {
            if (snode.getValue().size() != constructor.getParameterTypes().length) continue;
            possibleConstructors.add(constructor);
        }
        if (!possibleConstructors.isEmpty()) {
            int index;
            Object argumentList;
            if (possibleConstructors.size() == 1) {
                argumentList = new Object[snode.getValue().size()];
                Constructor c = (Constructor)possibleConstructors.get(0);
                index = 0;
                for (Node node2 : snode.getValue()) {
                    Class<?> type = c.getParameterTypes()[index];
                    node2.setType(type);
                    argumentList[index++] = Constructor.this.constructObject(node2);
                }
                try {
                    c.setAccessible(true);
                    return c.newInstance((Object[])argumentList);
                }
                catch (Exception e) {
                    throw new YAMLException(e);
                }
            }
            argumentList = Constructor.this.constructSequence(snode);
            Class[] parameterTypes = new Class[argumentList.size()];
            index = 0;
            Iterator iterator = argumentList.iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                parameterTypes[index] = e.getClass();
                ++index;
            }
            for (Constructor constructor : possibleConstructors) {
                Class<?>[] argTypes = constructor.getParameterTypes();
                boolean foundConstructor = true;
                for (int i = 0; i < argTypes.length; ++i) {
                    if (this.wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) continue;
                    foundConstructor = false;
                    break;
                }
                if (!foundConstructor) continue;
                try {
                    constructor.setAccessible(true);
                    return constructor.newInstance(argumentList.toArray());
                }
                catch (Exception e) {
                    throw new YAMLException(e);
                }
            }
        }
        throw new YAMLException("No suitable constructor with " + snode.getValue().size() + " arguments found for " + node.getType());
    }

    private Class<? extends Object> wrapIfPrimitive(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        }
        if (clazz == Integer.TYPE) {
            return Integer.class;
        }
        if (clazz == Float.TYPE) {
            return Float.class;
        }
        if (clazz == Double.TYPE) {
            return Double.class;
        }
        if (clazz == Boolean.TYPE) {
            return Boolean.class;
        }
        if (clazz == Long.TYPE) {
            return Long.class;
        }
        if (clazz == Character.TYPE) {
            return Character.class;
        }
        if (clazz == Short.TYPE) {
            return Short.class;
        }
        if (clazz == Byte.TYPE) {
            return Byte.class;
        }
        throw new YAMLException("Unexpected primitive " + clazz);
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
        SequenceNode snode = (SequenceNode)node;
        if (List.class.isAssignableFrom(node.getType())) {
            List list = (List)object;
            Constructor.this.constructSequenceStep2(snode, list);
        } else if (node.getType().isArray()) {
            Constructor.this.constructArrayStep2(snode, object);
        } else {
            throw new YAMLException("Immutable objects cannot be recursive.");
        }
    }
}
