package org.yaml.snakeyaml;

public enum DumperOptions$LineBreak {
    WIN("\r\n"),
    MAC("\r"),
    UNIX("\n");

    private final String lineBreak;

    private DumperOptions$LineBreak(String lineBreak) {
        this.lineBreak = lineBreak;
    }

    public String getString() {
        return this.lineBreak;
    }

    public String toString() {
        return "Line break: " + this.name();
    }

    public static DumperOptions$LineBreak getPlatformLineBreak() {
        String platformLineBreak = System.getProperty("line.separator");
        for (DumperOptions$LineBreak lb : DumperOptions$LineBreak.values()) {
            if (!lb.lineBreak.equals(platformLineBreak)) continue;
            return lb;
        }
        return UNIX;
    }
}
