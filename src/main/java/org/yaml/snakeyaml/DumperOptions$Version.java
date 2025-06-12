package org.yaml.snakeyaml;

public enum DumperOptions$Version {
    V1_0(new Integer[]{1, 0}),
    V1_1(new Integer[]{1, 1});

    private final Integer[] version;

    private DumperOptions$Version(Integer[] version) {
        this.version = version;
    }

    public int major() {
        return this.version[0];
    }

    public int minor() {
        return this.version[1];
    }

    public String getRepresentation() {
        return this.version[0] + "." + this.version[1];
    }

    public String toString() {
        return "Version: " + this.getRepresentation();
    }
}
