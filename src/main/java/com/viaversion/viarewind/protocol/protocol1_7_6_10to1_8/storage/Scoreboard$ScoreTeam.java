package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage;

class Scoreboard$ScoreTeam {
    private final String prefix;
    private final String suffix;
    private final String name;

    public Scoreboard$ScoreTeam(String name, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.name = name;
    }

    static String access$000(Scoreboard$ScoreTeam x0) {
        return x0.name;
    }
}
