package org.yaml.snakeyaml.env;

import java.util.regex.Matcher;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

class EnvScalarConstructor$ConstructEnv
extends AbstractConstruct {
    private EnvScalarConstructor$ConstructEnv() {
    }

    @Override
    public Object construct(Node node) {
        String val = EnvScalarConstructor.this.constructScalar((ScalarNode)node);
        Matcher matcher = ENV_FORMAT.matcher(val);
        matcher.matches();
        String name = matcher.group("name");
        String value = matcher.group("value");
        String separator = matcher.group("separator");
        return EnvScalarConstructor.this.apply(name, separator, value != null ? value : "", EnvScalarConstructor.this.getEnv(name));
    }
}
