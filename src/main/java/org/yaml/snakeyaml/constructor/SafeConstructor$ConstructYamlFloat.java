package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlFloat
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        String value = SafeConstructor.this.constructScalar((ScalarNode)node).replaceAll("_", "");
        if (value.isEmpty()) {
            throw new ConstructorException("while constructing a float", node.getStartMark(), "found empty value", node.getStartMark());
        }
        int sign = 1;
        char first = value.charAt(0);
        if (first == '-') {
            sign = -1;
            value = value.substring(1);
        } else if (first == '+') {
            value = value.substring(1);
        }
        String valLower = value.toLowerCase();
        if (".inf".equals(valLower)) {
            return sign == -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        if (".nan".equals(valLower)) {
            return Double.NaN;
        }
        if (value.indexOf(58) != -1) {
            String[] digits = value.split(":");
            int bes = 1;
            double val = 0.0;
            int j = digits.length;
            for (int i = 0; i < j; ++i) {
                val += Double.parseDouble(digits[j - i - 1]) * (double)bes;
                bes *= 60;
            }
            return (double)sign * val;
        }
        Double d = Double.valueOf(value);
        return d * (double)sign;
    }
}
