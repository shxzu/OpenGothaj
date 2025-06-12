package org.yaml.snakeyaml.internal;

import java.util.logging.Level;

public enum Logger$Level {
    WARNING(Level.FINE);

    private final Level level;

    private Logger$Level(Level level) {
        this.level = level;
    }

    static Level access$000(Logger$Level x0) {
        return x0.level;
    }
}
