package org.yaml.snakeyaml.error;

import org.yaml.snakeyaml.error.YAMLException;

public class MissingEnvironmentVariableException
extends YAMLException {
    public MissingEnvironmentVariableException(String message) {
        super(message);
    }
}
