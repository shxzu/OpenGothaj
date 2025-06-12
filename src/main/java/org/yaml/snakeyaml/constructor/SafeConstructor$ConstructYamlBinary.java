package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlBinary
extends AbstractConstruct {
    @Override
    public Object construct(Node node) {
        String noWhiteSpaces = SafeConstructor.this.constructScalar((ScalarNode)node).replaceAll("\\s", "");
        byte[] decoded = Base64Coder.decode(noWhiteSpaces.toCharArray());
        return decoded;
    }
}
