package org.yaml.snakeyaml.representer;

import java.math.BigInteger;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentNumber
implements Represent {
    protected SafeRepresenter$RepresentNumber() {
    }

    @Override
    public Node representData(Object data) {
        String value;
        Tag tag;
        if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof BigInteger) {
            tag = Tag.INT;
            value = data.toString();
        } else {
            Number number = (Number)data;
            tag = Tag.FLOAT;
            value = number.equals(Double.NaN) ? ".NaN" : (number.equals(Double.POSITIVE_INFINITY) ? ".inf" : (number.equals(Double.NEGATIVE_INFINITY) ? "-.inf" : number.toString()));
        }
        return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
    }
}
