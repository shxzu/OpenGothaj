package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlInt
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        String value = SafeConstructor.this.constructScalar((ScalarNode)node).replaceAll("_", "");
        if (value.isEmpty()) {
            throw new ConstructorException("while constructing an int", node.getStartMark(), "found empty value", node.getStartMark());
        }
        int sign = 1;
        char first = value.charAt(0);
        if (first == '-') {
            sign = -1;
            value = value.substring(1);
        } else if (first == '+') {
            value = value.substring(1);
        }
        int base = 10;
        if ("0".equals(value)) {
            return 0;
        }
        if (value.startsWith("0b")) {
            value = value.substring(2);
            base = 2;
        } else if (value.startsWith("0x")) {
            value = value.substring(2);
            base = 16;
        } else if (value.startsWith("0")) {
            value = value.substring(1);
            base = 8;
        } else {
            if (value.indexOf(58) != -1) {
                String[] digits = value.split(":");
                int bes = 1;
                int val = 0;
                int j = digits.length;
                for (int i = 0; i < j; ++i) {
                    val = (int)((long)val + Long.parseLong(digits[j - i - 1]) * (long)bes);
                    bes *= 60;
                }
                return SafeConstructor.this.createNumber(sign, String.valueOf(val), 10);
            }
            return SafeConstructor.this.createNumber(sign, value, 10);
        }
        return SafeConstructor.this.createNumber(sign, value, base);
    }
}
