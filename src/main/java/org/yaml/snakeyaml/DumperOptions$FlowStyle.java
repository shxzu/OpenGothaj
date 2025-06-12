package org.yaml.snakeyaml;

public enum DumperOptions$FlowStyle {
    FLOW(Boolean.TRUE),
    BLOCK(Boolean.FALSE),
    AUTO(null);

    private final Boolean styleBoolean;

    private DumperOptions$FlowStyle(Boolean flowStyle) {
        this.styleBoolean = flowStyle;
    }

    public String toString() {
        return "Flow style: '" + this.styleBoolean + "'";
    }
}
