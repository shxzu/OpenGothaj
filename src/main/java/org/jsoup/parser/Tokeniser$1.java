package org.jsoup.parser;

import org.jsoup.parser.TokeniserState;

class Tokeniser$1 {
    static final int[] $SwitchMap$org$jsoup$parser$TokeniserState;

    static {
        $SwitchMap$org$jsoup$parser$TokeniserState = new int[TokeniserState.values().length];
        try {
            Tokeniser$1.$SwitchMap$org$jsoup$parser$TokeniserState[TokeniserState.TagOpen.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Tokeniser$1.$SwitchMap$org$jsoup$parser$TokeniserState[TokeniserState.Data.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
