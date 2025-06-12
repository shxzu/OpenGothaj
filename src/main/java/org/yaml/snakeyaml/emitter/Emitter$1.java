package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.DumperOptions;

class Emitter$1 {
    static final int[] $SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle;

    static {
        $SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle = new int[DumperOptions.ScalarStyle.values().length];
        try {
            Emitter$1.$SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle[DumperOptions.ScalarStyle.DOUBLE_QUOTED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Emitter$1.$SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle[DumperOptions.ScalarStyle.SINGLE_QUOTED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Emitter$1.$SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle[DumperOptions.ScalarStyle.FOLDED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Emitter$1.$SwitchMap$org$yaml$snakeyaml$DumperOptions$ScalarStyle[DumperOptions.ScalarStyle.LITERAL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
