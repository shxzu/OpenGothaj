package org.yaml.snakeyaml;

import org.yaml.snakeyaml.error.YAMLException;

public enum DumperOptions$ScalarStyle {
    DOUBLE_QUOTED(Character.valueOf('\"')),
    SINGLE_QUOTED(Character.valueOf('\'')),
    LITERAL(Character.valueOf('|')),
    FOLDED(Character.valueOf('>')),
    PLAIN(null);

    private final Character styleChar;

    private DumperOptions$ScalarStyle(Character style) {
        this.styleChar = style;
    }

    public Character getChar() {
        return this.styleChar;
    }

    public String toString() {
        return "Scalar style: '" + this.styleChar + "'";
    }

    public static DumperOptions$ScalarStyle createStyle(Character style) {
        if (style == null) {
            return PLAIN;
        }
        switch (style.charValue()) {
            case '\"': {
                return DOUBLE_QUOTED;
            }
            case '\'': {
                return SINGLE_QUOTED;
            }
            case '|': {
                return LITERAL;
            }
            case '>': {
                return FOLDED;
            }
        }
        throw new YAMLException("Unknown scalar style character: " + style);
    }
}
