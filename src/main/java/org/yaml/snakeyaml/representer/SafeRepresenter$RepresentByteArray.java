package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentByteArray
implements Represent {
    protected SafeRepresenter$RepresentByteArray() {
    }

    @Override
    public Node representData(Object data) {
        char[] binary = Base64Coder.encode((byte[])data);
        return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), DumperOptions.ScalarStyle.LITERAL);
    }
}
