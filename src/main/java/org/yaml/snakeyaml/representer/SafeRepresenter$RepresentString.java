package org.yaml.snakeyaml.representer;

import java.nio.charset.StandardCharsets;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter$RepresentString
implements Represent {
    protected SafeRepresenter$RepresentString() {
    }

    @Override
    public Node representData(Object data) {
        Tag tag = Tag.STR;
        DumperOptions.ScalarStyle style = SafeRepresenter.this.defaultScalarStyle;
        String value = data.toString();
        if (SafeRepresenter.this.nonPrintableStyle == DumperOptions.NonPrintableStyle.BINARY && !StreamReader.isPrintable(value)) {
            tag = Tag.BINARY;
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            String checkValue = new String(bytes, StandardCharsets.UTF_8);
            if (!checkValue.equals(value)) {
                throw new YAMLException("invalid string value has occurred");
            }
            char[] binary = Base64Coder.encode(bytes);
            value = String.valueOf(binary);
            style = DumperOptions.ScalarStyle.LITERAL;
        }
        if (SafeRepresenter.this.defaultScalarStyle == DumperOptions.ScalarStyle.PLAIN && MULTILINE_PATTERN.matcher(value).find()) {
            style = DumperOptions.ScalarStyle.LITERAL;
        }
        return SafeRepresenter.this.representScalar(tag, value, style);
    }
}
