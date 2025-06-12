package org.yaml.snakeyaml.constructor;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.util.EnumUtils;

class Constructor$ConstructScalar
extends AbstractConstruct {
    protected Constructor$ConstructScalar() {
    }

    @Override
    public Object construct(Node nnode) {
        Object result;
        ScalarNode node = (ScalarNode)nnode;
        Class<? extends Object> type = node.getType();
        Object instance = Constructor.this.newInstance(type, node, false);
        if (instance != BaseConstructor.NOT_INSTANTIATED_OBJECT) {
            return instance;
        }
        if (type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type) || type == Boolean.class || Date.class.isAssignableFrom(type) || type == Character.class || type == BigInteger.class || type == BigDecimal.class || Enum.class.isAssignableFrom(type) || Tag.BINARY.equals(node.getTag()) || Calendar.class.isAssignableFrom(type) || type == UUID.class) {
            result = this.constructStandardJavaInstance(type, node);
        } else {
            Object argument;
            Constructor<?>[] javaConstructors = type.getDeclaredConstructors();
            int oneArgCount = 0;
            Constructor<Object> javaConstructor = null;
            for (Constructor<?> c : javaConstructors) {
                if (c.getParameterTypes().length != 1) continue;
                ++oneArgCount;
                javaConstructor = c;
            }
            if (javaConstructor == null) {
                throw new YAMLException("No single argument constructor found for " + type);
            }
            if (oneArgCount == 1) {
                argument = this.constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
            } else {
                argument = Constructor.this.constructScalar(node);
                try {
                    javaConstructor = type.getDeclaredConstructor(String.class);
                }
                catch (Exception e) {
                    throw new YAMLException("Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), e);
                }
            }
            try {
                javaConstructor.setAccessible(true);
                result = javaConstructor.newInstance(argument);
            }
            catch (Exception e) {
                throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
            }
        }
        return result;
    }

    private Object constructStandardJavaInstance(Class type, ScalarNode node) {
        Object result;
        if (type == String.class) {
            Construct stringConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
            result = stringConstructor.construct(node);
        } else if (type == Boolean.class || type == Boolean.TYPE) {
            Construct boolConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.BOOL);
            result = boolConstructor.construct(node);
        } else if (type == Character.class || type == Character.TYPE) {
            Construct charConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
            String ch = (String)charConstructor.construct(node);
            if (ch.length() == 0) {
                result = null;
            } else {
                if (ch.length() != 1) {
                    throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
                }
                result = Character.valueOf(ch.charAt(0));
            }
        } else if (Date.class.isAssignableFrom(type)) {
            Construct dateConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.TIMESTAMP);
            Date date = (Date)dateConstructor.construct(node);
            if (type == Date.class) {
                result = date;
            } else {
                try {
                    Constructor constr = type.getConstructor(Long.TYPE);
                    result = constr.newInstance(date.getTime());
                }
                catch (RuntimeException e) {
                    throw e;
                }
                catch (Exception e) {
                    throw new YAMLException("Cannot construct: '" + type + "'");
                }
            }
        } else if (type == Float.class || type == Double.class || type == Float.TYPE || type == Double.TYPE || type == BigDecimal.class) {
            if (type == BigDecimal.class) {
                result = new BigDecimal(node.getValue());
            } else {
                Construct doubleConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.FLOAT);
                result = doubleConstructor.construct(node);
                if (type == Float.class || type == Float.TYPE) {
                    result = Float.valueOf(((Double)result).floatValue());
                }
            }
        } else if (type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == BigInteger.class || type == Byte.TYPE || type == Short.TYPE || type == Integer.TYPE || type == Long.TYPE) {
            Construct intConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.INT);
            result = intConstructor.construct(node);
            result = type == Byte.class || type == Byte.TYPE ? (Number)Integer.valueOf(result.toString()).byteValue() : (Number)(type == Short.class || type == Short.TYPE ? (Number)Integer.valueOf(result.toString()).shortValue() : (Number)(type == Integer.class || type == Integer.TYPE ? (Number)Integer.parseInt(result.toString()) : (Number)(type == Long.class || type == Long.TYPE ? Long.valueOf(result.toString()) : new BigInteger(result.toString()))));
        } else if (Enum.class.isAssignableFrom(type)) {
            String enumValueName = node.getValue();
            try {
                if (Constructor.this.loadingConfig.isEnumCaseSensitive()) {
                    result = Enum.valueOf(type, enumValueName);
                }
                result = EnumUtils.findEnumInsensitiveCase(type, enumValueName);
            }
            catch (Exception ex) {
                throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
            }
        } else if (Calendar.class.isAssignableFrom(type)) {
            SafeConstructor.ConstructYamlTimestamp contr = new SafeConstructor.ConstructYamlTimestamp();
            contr.construct(node);
            result = contr.getCalendar();
        } else if (Number.class.isAssignableFrom(type)) {
            SafeConstructor.ConstructYamlFloat contr = new SafeConstructor.ConstructYamlFloat(Constructor.this);
            result = contr.construct(node);
        } else if (UUID.class == type) {
            result = UUID.fromString(node.getValue());
        } else if (Constructor.this.yamlConstructors.containsKey(node.getTag())) {
            result = ((Construct)Constructor.this.yamlConstructors.get(node.getTag())).construct(node);
        } else {
            throw new YAMLException("Unsupported class: " + type);
        }
        return result;
    }
}
